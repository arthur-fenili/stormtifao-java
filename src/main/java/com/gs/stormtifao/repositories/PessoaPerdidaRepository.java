package com.gs.stormtifao.repositories;

import com.gs.stormtifao.models.PessoaPerdida;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PessoaPerdidaRepository extends JpaRepository<PessoaPerdida, Long> {

    List<PessoaPerdida> findByNomeContaining(String nome);
}
