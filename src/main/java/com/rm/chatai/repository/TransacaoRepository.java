package com.rm.chatai.repository;

import com.rm.chatai.domain.Transacao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransacaoRepository  extends JpaRepository<Transacao, Integer> {
    List<Transacao> findByContaIdOrderByDataDesc(Long contaId);
    List<Transacao> findByContaIdAndTipo(Long contaId, String tipo);
    List<Transacao> findByDescricaoContainingIgnoreCase(String descricao);
}
