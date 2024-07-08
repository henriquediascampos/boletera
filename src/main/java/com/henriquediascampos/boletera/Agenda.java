package com.henriquediascampos.boletera;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Agenda {
    private Integer codigo;
    private Integer paciente;
    private Integer convenio;
    private String telefone;
    private Integer categoria;
    private Integer especialidade;
    private Integer procedimento;
}
