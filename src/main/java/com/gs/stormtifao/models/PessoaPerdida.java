package com.gs.stormtifao.models;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;


@Data
@Entity
public class PessoaPerdida {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private Integer idade;
    @Lob
    private String fotoBase64;
    private LocalDate dataDesaparecimento;
    private String localDesaparecimento;
    private String telefoneContato;
}
