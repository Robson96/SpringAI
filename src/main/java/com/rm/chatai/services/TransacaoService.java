package com.rm.chatai.services;

import com.rm.chatai.domains.Conta;
import com.rm.chatai.domains.Transacao;
import com.rm.chatai.repositorys.TransacaoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransacaoService {

    private final TransacaoRepository transacaoRepository;
    private final ContaService contaService;
    public TransacaoService(TransacaoRepository transacaoRepository, ContaService contaService) {
        this.transacaoRepository = transacaoRepository;
        this.contaService = contaService;
    }

    public List<Transacao> listarTransacoes(String descricao) {
        Conta conta = contaService.buscarConta();

        if (descricao == null || descricao.isBlank()) {
            return transacaoRepository.findByContaIdOrderByDataDesc(conta.getId());
        }
        return transacaoRepository.findByDescricaoContainingIgnoreCase(descricao);
    }
}
