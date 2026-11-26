package com.rm.chatai.services;

import com.rm.chatai.domains.CompraPendente;
import com.rm.chatai.repositorys.CompraPendenteRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompraPendenteService {

    private final CompraPendenteRepository compraPendenteRepository;

    public CompraPendenteService(CompraPendenteRepository compraPendenteRepository) {
        this.compraPendenteRepository = compraPendenteRepository;
    }

    public List<CompraPendente> buscarPorEstabelecimento(String estabelecimento) {
        return compraPendenteRepository.findByEstabelecimentoContainingIgnoreCase(estabelecimento);
    }
}
