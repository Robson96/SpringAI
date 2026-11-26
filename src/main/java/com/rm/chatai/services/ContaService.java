package com.rm.chatai.services;

import com.rm.chatai.domains.Conta;
import com.rm.chatai.repositorys.ContaRepository;
import org.springframework.stereotype.Service;

@Service
public class ContaService {

    private final ContaRepository contaRepository;
    public ContaService(ContaRepository contaRepository) {
        this.contaRepository = contaRepository;
    }

    public Conta buscarConta() {
        return contaRepository.findAll().stream()
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Conta nao encontrada"));
    }
}
