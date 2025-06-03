package com.gs.stormtifao.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gs.stormtifao.models.PessoaPerdida;
import com.gs.stormtifao.rabbitmq.RabbitMqProducer;
import com.gs.stormtifao.repositories.PessoaPerdidaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PessoaPerdidaServiceTest {

    @Mock
    private PessoaPerdidaRepository repository;

    @Mock
    private RabbitMqProducer rabbitMqProducer;

    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private PessoaPerdidaService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        service.rabbitMqProducer = rabbitMqProducer;
        service.objectMapper = new ObjectMapper();
    }

    @Test
    void listarTodas_deveRetornarLista() {
        List<PessoaPerdida> lista = Arrays.asList(new PessoaPerdida(), new PessoaPerdida());
        when(repository.findAll()).thenReturn(lista);

        List<PessoaPerdida> resultado = service.listarTodas();

        assertEquals(2, resultado.size());
        verify(repository).findAll();
    }

    @Test
    void buscarPorId_deveRetornarOptional() {
        PessoaPerdida pessoa = new PessoaPerdida();
        pessoa.setId(1L);
        when(repository.findById(1L)).thenReturn(Optional.of(pessoa));

        Optional<PessoaPerdida> resultado = service.buscarPorId(1L);

        assertTrue(resultado.isPresent());
        assertEquals(1L, resultado.get().getId());
        verify(repository).findById(1L);
    }

    @Test
    void salvar_deveSalvarPessoaEEnviarRabbit() {
        PessoaPerdida pessoa = new PessoaPerdida();
        pessoa.setId(1L);
        when(repository.save(pessoa)).thenReturn(pessoa);

        service.salvar(pessoa);

        verify(repository).save(pessoa);
        verify(rabbitMqProducer).sendPessoaPerdidaJson(anyString());
    }

    @Test
    void deletar_deveChamarDeleteById() {
        service.deletar(5L);

        verify(repository).deleteById(5L);
    }
}