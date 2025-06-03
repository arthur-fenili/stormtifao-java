package com.gs.stormtifao.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;


@Data
@Entity
public class PessoaPerdida {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O nome é obrigatório")
    private String nome;

    @NotNull(message = "A idade é obrigatória")
    @Min(value = 0, message = "A idade não pode ser negativa")
    @Max(value = 120, message = "A idade não pode ser maior que 120")
    private Integer idade;
    @Lob
    private String fotoBase64;

    @NotNull(message = "A data de desaparecimento é obrigatória")
    @PastOrPresent(message = "A data do desaparecimento não pode ser no futuro")
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDate dataDesaparecimento;
    @NotBlank(message = "O local de desaparecimento é obrigatório")
    private String localDesaparecimento;
    @NotBlank(message = "O telefone de contato é obrigatório")
    @Pattern(regexp = "^\\d{8,15}$", message = "O telefone deve conter apenas números (8 a 15 dígitos)")
    private String telefoneContato;
}
