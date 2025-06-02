package com.gs.stormtifao.services;

import com.gs.stormtifao.models.PessoaPerdida;
import com.gs.stormtifao.repositories.PessoaPerdidaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PessoaPerdidaService {
    private final PessoaPerdidaRepository repository;

    public PessoaPerdidaService(PessoaPerdidaRepository repository) {
        this.repository = repository;
    }

    public List<PessoaPerdida> listarTodas() {
        return repository.findAll();
    }

    public Optional<PessoaPerdida> buscarPorId(Long id) {
        return repository.findById(id);
    }

    public PessoaPerdida salvar(PessoaPerdida pessoa) {
        return repository.save(pessoa);
    }

    public void deletar(Long id) {
        repository.deleteById(id);
    }
}
