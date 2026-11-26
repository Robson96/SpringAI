package com.rm.chatai.config;

import com.rm.chatai.domains.CartaoCredito;
import com.rm.chatai.domains.Cliente;
import com.rm.chatai.domains.CompraPendente;
import com.rm.chatai.domains.Conta;
import com.rm.chatai.domains.Parcela;
import com.rm.chatai.domains.Transacao;
import com.rm.chatai.repositorys.CartaoCreditoRepository;
import com.rm.chatai.repositorys.ClienteRepository;
import com.rm.chatai.repositorys.CompraPendenteRepository;
import com.rm.chatai.repositorys.ContaRepository;
import com.rm.chatai.repositorys.ParcelaRepository;
import com.rm.chatai.repositorys.TransacaoRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;

@Configuration
public class DataLoader {

    @Bean
    CommandLineRunner popular(ClienteRepository clienteRepo,
                              ContaRepository contaRepo,
                              TransacaoRepository transacaoRepo,
                              CartaoCreditoRepository cartaoRepo,
                              CompraPendenteRepository compraRepo,
                              ParcelaRepository parcelaRepo) {
        return args -> {
            Cliente joao = new Cliente();
            joao.setNome("João da Silva");
            joao.setCpf("123.456.789-00");
            joao.setEmail("joao@email.com");
            joao.setDataNascimento(LocalDate.of(1990, 5, 15));
            joao = clienteRepo.save(joao);

            Conta conta = new Conta();
            conta.setCliente(joao);
            conta.setAgencia("0001");
            conta.setNumero("12345-6");
            conta.setSaldoDisponivel(1500.00);
            conta.setLimiteChequeEspecial(500.00);
            conta.setTipo("CORRENTE");
            conta = contaRepo.save(conta);

            transacaoRepo.save(new Transacao(null, conta.getId(), "Salário", 3500.00,
                    LocalDate.now().minusDays(5), "CREDITO", "SALARIO"));
            transacaoRepo.save(new Transacao(null, conta.getId(), "Supermercado Extra", 180.50,
                    LocalDate.now().minusDays(4), "DEBITO", "COMPRA"));
            transacaoRepo.save(new Transacao(null, conta.getId(), "Netflix", 39.90,
                    LocalDate.now().minusDays(3), "DEBITO", "ASSINATURA"));
            transacaoRepo.save(new Transacao(null, conta.getId(), "PIX recebido - Maria", 200.00,
                    LocalDate.now().minusDays(2), "CREDITO", "TRANSFERENCIA"));
            transacaoRepo.save(new Transacao(null, conta.getId(), "Conta de luz", 95.30,
                    LocalDate.now().minusDays(1), "DEBITO", "PAGAMENTO"));

            CartaoCredito cartao = new CartaoCredito();
            cartao.setContaId(conta.getId());
            cartao.setNumeroMascarado("**** 1234");
            cartao.setBandeira("VISA");
            cartao.setLimiteTotal(5000.00);
            cartao.setLimiteDisponivel(3200.00);
            cartao.setFaturaAtual(1800.00);
            cartao.setVencimentoFatura(LocalDate.now().plusDays(10));
            cartao.setFechamentoFatura(LocalDate.now().plusDays(3));
            cartao = cartaoRepo.save(cartao);

            CompraPendente amazon = new CompraPendente();
            amazon.setContaId(conta.getId());
            amazon.setEstabelecimento("Amazon");
            amazon.setValor(250.00);
            amazon.setStatus("PROCESSANDO");
            amazon.setDataCompra(LocalDate.now().minusDays(1));
            amazon.setDataPrevistaDebito(LocalDate.now().plusDays(2));
            compraRepo.save(amazon);

            parcelaRepo.save(new Parcela(null, cartao.getId(), "Notebook",
                    416.66, 3, 10, LocalDate.now().plusMonths(2)));
            parcelaRepo.save(new Parcela(null, cartao.getId(), "Geladeira",
                    250.00, 2, 6, LocalDate.now().plusMonths(1)));
        };
    }
}