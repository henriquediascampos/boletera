package com.henriquediascampos.boletera;

import java.time.LocalDate;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Parcela {
    private Double valor;
    private LocalDate data;
    private Integer parcela;
}
