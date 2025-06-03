package com.gs.stormtifao.controllers;

import com.gs.stormtifao.models.PessoaPerdida;
import com.gs.stormtifao.rabbitmq.RabbitMqProducer;
import com.gs.stormtifao.repositories.PessoaPerdidaRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

import java.time.LocalDate;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class PessoaPerdidaControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PessoaPerdidaRepository repository;

    @Autowired
    private RabbitMqProducer rabbitMqProducer;

    @TestConfiguration
    static class MockConfig {
        @Bean
        public RabbitMqProducer rabbitMqProducer() {
            return mock(RabbitMqProducer.class);
        }
    }

    @Test
    @WithMockUser(username = "testuser", roles = {"USER"})
    void deveCadastrarPessoaEEnviarRabbit() throws Exception {
        mockMvc.perform(multipart("/pessoas-perdidas")
                        .param("nome", "Fulano")
                        .param("idade", "30")
                        .param("dataDesaparecimento", LocalDate.now().toString())
                        .param("localDesaparecimento", "Endereço de teste")
                        .param("telefoneContato", "123456789")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/pessoas-perdidas"));

        verify(rabbitMqProducer, atLeastOnce()).sendPessoaPerdidaJson(anyString());
    }

    @Test
    @WithMockUser(username = "testuser", roles = {"USER"})
    void deveDeletarPessoa() throws Exception {
        PessoaPerdida pessoa = new PessoaPerdida();
        pessoa.setNome("Ciclano");
        pessoa.setIdade(40);
        pessoa.setDataDesaparecimento(LocalDate.now());
        pessoa.setLocalDesaparecimento("Endereço de teste");
        pessoa.setTelefoneContato("123456789");
        pessoa = repository.save(pessoa);

        mockMvc.perform(get("/pessoas-perdidas/deletar/" + pessoa.getId()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/pessoas-perdidas"));
    }
}