package com.rm.chatai.repository;

import com.rm.chatai.domain.CartaoCredito;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartaoCreditoRepository extends JpaRepository<CartaoCredito, Long> {
    Optional<CartaoCredito> findByContaId(Long contaId);
}
