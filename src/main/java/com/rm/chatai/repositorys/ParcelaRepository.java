package com.rm.chatai.repositorys;

import com.rm.chatai.domains.Parcela;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ParcelaRepository extends JpaRepository<Parcela, Long> {
    List<Parcela> findByCartaoIdOrderByDataVencimentoAsc(Long cartaoId);
    List<Parcela> findByDescricaoContainingIgnoreCase(String descricao);
}
