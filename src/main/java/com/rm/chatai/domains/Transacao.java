package com.rm.chatai.domains;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDate;

@Entity
public class Transacao {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long contaId;
    private String descricao;
    private Double valor;
    private LocalDate data;
    private String tipo;
    private String categoria;

    public Transacao(Long id, Long contaId, String descricao, Double valor, LocalDate data, String tipo, String categoria) {
        this.id = id;
        this.contaId = contaId;
        this.descricao = descricao;
        this.valor = valor;
        this.data = data;
        this.tipo = tipo;
        this.categoria = categoria;
    }
}
