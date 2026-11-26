package com.rm.chatai.services;

import com.rm.chatai.domains.CartaoCredito;
import com.rm.chatai.repositorys.CartaoCreditoRepository;
import org.springframework.stereotype.Service;

@Service
public class CartaoCreditoService {

    private final CartaoCreditoRepository cartaoCreditoRepository;

    public CartaoCreditoService(CartaoCreditoRepository cartaoCreditoRepository) {
        this.cartaoCreditoRepository = cartaoCreditoRepository;
    }

    public CartaoCredito buscarCartao() {
        return cartaoCreditoRepository.findAll().stream()
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Cartão não encontrado"));
    }
}
