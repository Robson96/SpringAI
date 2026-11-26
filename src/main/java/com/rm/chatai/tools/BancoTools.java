package com.rm.chatai.tools;

import com.rm.chatai.domains.CompraPendente;
import com.rm.chatai.domains.Parcela;
import com.rm.chatai.domains.Transacao;
import com.rm.chatai.services.CartaoCreditoService;
import com.rm.chatai.services.CompraPendenteService;
import com.rm.chatai.services.ContaService;
import com.rm.chatai.services.ParcelaService;
import com.rm.chatai.services.TransacaoService;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class BancoTools {

    private final ContaService contaService;
    private final CompraPendenteService compraPendenteService;
    private final CartaoCreditoService cartaoCreditoService;
    private final TransacaoService transacaoService;
    private final ParcelaService parcelaService;
    public BancoTools(ContaService contaService,
                      CompraPendenteService compraPendenteService,
                      CartaoCreditoService cartaoCreditoService,
                      TransacaoService transacaoService,
                      ParcelaService parcelaService) {
        this.contaService = contaService;
        this.compraPendenteService = compraPendenteService;
        this.cartaoCreditoService = cartaoCreditoService;
        this.transacaoService = transacaoService;
        this.parcelaService = parcelaService;
    }

    @Tool(description = "Consulta o status de uma compra pendente pelo nome do estabelecimento." +
            "Use quando o cliente perguntar o sobre uma compra que ainda nao foi debitada da conta," +
            "como a compra a amazon")
    public String consultarCompraPendente(
            @ToolParam(description = "Nome do estabelecimento, por exemplo: Amazon, Netflix, Marcado Livre")
            String estabelecimento
    ) {
        List<CompraPendente> compras = compraPendenteService.buscarPorEstabelecimento(estabelecimento);

        if (compras.isEmpty()) {
            return "Nenhuma compra pendente encontrada para: " + estabelecimento;
        }

        return compras.stream()
                .map(c -> String.format(
                        "Compra em %s: R$ %.2f | Status: %s | Previsão de debito: %s",
                        c.getEstabelecimento(), c.getValor(), c.getStatus(), c.getDataPrevistaDebito()
                )).collect(Collectors.joining("\n"));
    }

    @Tool(description = "Retorna o saldo disponivel e o limite de cheque especial da conta" +
            " corrente do cliente ")
    public String consultarContaCorrente() {
        var conta = contaService.buscarConta();
        return String.format("Saldo disponível: R$ %.2f | Limite de cheque especial: R$ %.2f",
                conta.getSaldoDisponivel(), conta.getLimiteChequeEspecial());
    }

    @Tool(description = "Retorna o valor da fatura atual, a data de vencimento e o limite disponível do cartão de crédito.")
    public String consultarFaturaCartao() {
        var cartao = cartaoCreditoService.buscarCartao();
        return String.format(
                "Cartão %s | Fatura atual: R$ %.2f | Vencimento: %s | Limite disponível: R$ %.2f",
                cartao.getNumeroMascarado(), cartao.getFaturaAtual(),
                cartao.getVencimentoFatura(), cartao.getLimiteDisponivel());
    }

    @Tool(description = "Lista as transações da conta. Se o cliente informar uma descrição (ex: Netflix, supermercado)," +
            " filtra por ela. Caso contrário, retorna as transações mais recentes.")
    public String consultarTransacoes(
            @ToolParam(required = false, description = "Descrição parcial da transação, " +
                    "por exemplo: Netflix, luz, supermecado")
            String descricao
    ) {
        List<Transacao> transacoes = transacaoService.listarTransacoes(descricao);

        if  (transacoes.isEmpty()) {
            return "Nenhuma transação encontrada!" + (descricao != null ? " para: " + descricao : "");
        }

        return transacoes.stream()
                .map(t -> String.format("%s | %s | R$ %.2f | %s",
                        t.getData(), t.getDescricao(), t.getValor(), t.getTipo()))
                .collect(Collectors.joining("\n"));
    }

    @Tool(description = "Lista as parcelas do cartão de crédito. Se o cliente informar uma descrição " +
            "(ex: notebook, geladeira), filtra por ela. Caso contrário, retorna todas as parcelas.")
    public String consultarParcelas(
            @ToolParam(required = false, description = "Descrição parcial da compra parcelada, por exemplo: notebook, geladeira")
            String descricao
    ) {
        List<Parcela> parcelas = parcelaService.listarParcelas(descricao);

        if (parcelas.isEmpty()) {
            return "Nenhuma parcela encontrada" + (descricao != null ? " para: " + descricao : ".");
        }

        return parcelas.stream()
                .map(p -> String.format("%s: parcela %d/%d | R$ %.2f | Vencimento: %s",
                        p.getDescricao(), p.getNumeroParcela(), p.getTotalParcelas(),
                        p.getValorParcela(), p.getDataVencimento()))
                .collect(Collectors.joining("\n"));
    }
}
