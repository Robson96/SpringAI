package com.rm.chatai.repository;

import com.rm.chatai.domain.CompraPendente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CompraPendenteRepository extends JpaRepository<CompraPendente, Long> {
    List<CompraPendente> findByEstabelecimentoContainingIgnoreCase(String estabelecimento);
    List<CompraPendente> findByContaIdAndStatus(Long contaId, String status);
}
