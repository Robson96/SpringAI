package com.rm.chatai.repository;

import com.rm.chatai.domain.Parcela;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ParcelaRepository extends JpaRepository<Parcela, Long> {
    List<Parcela> findByCartaoIdOrderByDataVencimentoAsc(Long cartaoId);
    List<Parcela> findByDescricaoContainingIgnoreCase(String descricao);
}
