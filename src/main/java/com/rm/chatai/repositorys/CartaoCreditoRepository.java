package com.rm.chatai.repositorys;

import com.rm.chatai.domains.CartaoCredito;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartaoCreditoRepository extends JpaRepository<CartaoCredito, Long> {
    Optional<CartaoCredito> findByContaId(Long contaId);
}
