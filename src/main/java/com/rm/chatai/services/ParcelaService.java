package com.rm.chatai.services;

import com.rm.chatai.domains.CartaoCredito;
import com.rm.chatai.domains.Parcela;
import com.rm.chatai.repositorys.ParcelaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ParcelaService {

    private final ParcelaRepository parcelaRepository;
    private final CartaoCreditoService cartaoCreditoService;
    public ParcelaService(ParcelaRepository parcelaRepository, CartaoCreditoService cartaoCreditoService) {
        this.parcelaRepository = parcelaRepository;
        this.cartaoCreditoService = cartaoCreditoService;
    }

    public List<Parcela> listarParcelas(String descricao) {
        CartaoCredito cartao = cartaoCreditoService.buscarCartao();

        if (descricao == null || descricao.isBlank()) {
            return parcelaRepository.findByCartaoIdOrderByDataVencimentoAsc(cartao.getId());
        }
        return parcelaRepository.findByDescricaoContainingIgnoreCase(descricao);
    }
}
