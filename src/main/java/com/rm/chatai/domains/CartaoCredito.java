package com.rm.chatai.domains;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDate;

@Entity
public class CartaoCredito {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long contaId;
    private String numeroMascarado;
    private String bandeira;
    private Double limiteTotal;
    private Double limiteDisponivel;
    private Double faturaAtual;
    private LocalDate vencimentoFatura;
    private LocalDate fechamentoFatura;

    public String getBandeira() {
        return bandeira;
    }

    public void setBandeira(String bandeira) {
        this.bandeira = bandeira;
    }

    public LocalDate getFechamentoFatura() {
        return fechamentoFatura;
    }

    public void setFechamentoFatura(LocalDate fechamentoFatura) {
        this.fechamentoFatura = fechamentoFatura;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getLimiteDisponivel() {
        return limiteDisponivel;
    }

    public void setLimiteDisponivel(Double limiteDisponivel) {
        this.limiteDisponivel = limiteDisponivel;
    }

    public Long getContaId() {
        return contaId;
    }

    public void setContaId(Long contaId) {
        this.contaId = contaId;
    }

    public String getNumeroMascarado() {
        return numeroMascarado;
    }

    public void setNumeroMascarado(String numeroMascarado) {
        this.numeroMascarado = numeroMascarado;
    }

    public Double getLimiteTotal() {
        return limiteTotal;
    }

    public void setLimiteTotal(Double limiteTotal) {
        this.limiteTotal = limiteTotal;
    }

    public Double getFaturaAtual() {
        return faturaAtual;
    }

    public void setFaturaAtual(Double faturaAtual) {
        this.faturaAtual = faturaAtual;
    }

    public LocalDate getVencimentoFatura() {
        return vencimentoFatura;
    }

    public void setVencimentoFatura(LocalDate vencimentoFatura) {
        this.vencimentoFatura = vencimentoFatura;
    }
}
