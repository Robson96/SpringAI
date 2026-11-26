package com.rm.chatai.controllers;

import com.rm.chatai.domains.CartaoCredito;
import com.rm.chatai.domains.CompraPendente;
import com.rm.chatai.domains.Conta;
import com.rm.chatai.domains.Parcela;
import com.rm.chatai.domains.Transacao;
import com.rm.chatai.services.CartaoCreditoService;
import com.rm.chatai.services.CompraPendenteService;
import com.rm.chatai.services.ContaService;
import com.rm.chatai.services.ParcelaService;
import com.rm.chatai.services.TransacaoService;
import com.rm.chatai.tools.BancoTools;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import org.springframework.http.MediaType;

import java.util.List;

@RestController
@RequestMapping("/api")
public class BancoController {

    private final ContaService contaService;
    private final CompraPendenteService compraPendenteService;
    private final CartaoCreditoService cartaoCreditoService;
    private final TransacaoService transacaoService;
    private final ParcelaService parcelaService;
    private final ChatClient chatClient;
    public BancoController(ContaService contaService,
                           CompraPendenteService compraPendenteService,
                           CartaoCreditoService cartaoCreditoService,
                           TransacaoService transacaoService,
                           ParcelaService parcelaService,
                           ChatClient.Builder builder,
                           BancoTools tools) {
        this.contaService = contaService;
        this.compraPendenteService = compraPendenteService;
        this.cartaoCreditoService = cartaoCreditoService;
        this.transacaoService = transacaoService;
        this.parcelaService = parcelaService;
        this.chatClient = builder.defaultSystem("""
                        Você é um assistente bancário. Responda de forma clara e objetiva, em português.
                                                Sempre consulte os dados reais usando as ferramentas disponíveis antes de responder.
                                                Nunca invente informações. Se uma ferramenta não retornar dados, diga que não encontrou.
                        """)
                .defaultTools(tools)
                .build();
    }

    @GetMapping("/conta")
    public Conta conta() {
        return contaService.buscarConta();
    }

    @GetMapping("/compras-pendentes")
    public List<CompraPendente> comprasPendentes(@RequestParam String estabelecimento) {
        return compraPendenteService.buscarPorEstabelecimento(estabelecimento);
    }

    @GetMapping("/cartao")
    public CartaoCredito cartao() {
        return cartaoCreditoService.buscarCartao();
    }

    @GetMapping("/transacoes")
    public List<Transacao> transacoes(@RequestParam(required = false) String descricao) {
        return transacaoService.listarTransacoes(descricao);
    }

    @GetMapping("/parcelas")
    public List<Parcela> parcelas(@RequestParam(required = false) String descricao) {
        return parcelaService.listarParcelas(descricao);
    }

    @PostMapping("/chat")
    public String chat(@RequestBody ChatRequest request) {
        return chatClient.prompt()
                .user(request.pergunta)
                .call()
                .content();
    }

    @PostMapping(value = "/chat/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> chatStream(@RequestBody ChatRequest request) {
        return chatClient.prompt()
                .user(request.pergunta())
                .stream()
                .content();
    }

    public record ChatRequest(String pergunta) {}
}
