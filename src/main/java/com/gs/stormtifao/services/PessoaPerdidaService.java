package com.gs.stormtifao.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.gs.stormtifao.models.PessoaPerdida;
import com.gs.stormtifao.rabbitmq.RabbitMqProducer;
import com.gs.stormtifao.repositories.PessoaPerdidaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PessoaPerdidaService {
    private final PessoaPerdidaRepository repository;

    @Autowired
    RabbitMqProducer rabbitMqProducer;

    @Autowired
    ObjectMapper objectMapper;

    public PessoaPerdidaService(PessoaPerdidaRepository repository) {
        this.repository = repository;
    }

    public List<PessoaPerdida> listarTodas() {
        return repository.findAll();
    }

    public Optional<PessoaPerdida> buscarPorId(Long id) {
        return repository.findById(id);
    }

    public void salvar(PessoaPerdida pessoa) {
        PessoaPerdida salva = repository.save(pessoa);
        try {
            ObjectNode node = objectMapper.valueToTree(salva);
            node.remove("fotoBase64");
            String json = objectMapper.writeValueAsString(node);
            rabbitMqProducer.sendPessoaPerdidaJson(json);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void deletar(Long id) {
        repository.deleteById(id);
    }
}
