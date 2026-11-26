const btnIniciar = document.getElementById('btn-iniciar-chat');
const chatArea = document.getElementById('chat-area');
const mensagens = document.getElementById('mensagens');
const inputPergunta = document.getElementById('pergunta');
let chatIniciado = false;

// Carrega dados da conta ao abrir a página
async function carregarDados() {
    try {
        const [conta, cartao] = await Promise.all([
            fetch('/api/conta').then(r => r.json()),
            fetch('/api/cartao').then(r => r.json())
        ]);

        document.getElementById('titular').textContent = conta.cliente?.nome ?? '—';
        document.getElementById('agencia-conta').textContent =
            `${conta.agencia} / ${conta.numero}`;
        document.getElementById('saldo').textContent = formatarMoeda(conta.saldoDisponivel);
        document.getElementById('fatura').textContent = formatarMoeda(cartao.faturaAtual);
        document.getElementById('vencimento').textContent = formatarData(cartao.vencimentoFatura);
        document.getElementById('limite').textContent = formatarMoeda(cartao.limiteDisponivel);
    } catch (e) {
        console.error('Erro ao carregar dados:', e);
    }
}

function formatarMoeda(v) {
    return Number(v).toLocaleString('pt-BR', { style: 'currency', currency: 'BRL' });
}

function formatarData(d) {
    if (!d) return '—';
    const [ano, mes, dia] = d.split('-');
    return `${dia}/${mes}/${ano}`;
}

// Botão Iniciar Chat
btnIniciar.addEventListener('click', () => {
    chatIniciado = true;
    chatArea.classList.remove('hidden');
    btnIniciar.disabled = true;
    btnIniciar.textContent = '💬 Chat ativo';
    adicionarMensagem('bot',
        'Olá! Sou o assistente do Banco Demo. Posso consultar seu saldo, fatura do cartão, compras pendentes, transações e parcelas. Como posso ajudar?');
    inputPergunta.focus();
});

// Sugestões de pergunta
function sugerir(texto) {
    inputPergunta.value = texto;
    enviarMensagem();
}

// Envio de mensagem
async function enviarMensagem() {
    const pergunta = inputPergunta.value.trim();
    if (!pergunta) return;

    adicionarMensagem('cliente', pergunta);
    inputPergunta.value = '';

    const msgDigitando = adicionarMensagem('digitando', 'Consultando dados...');

    try {
        const response = await fetch('/api/chat', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ pergunta })
        });

        const resposta = await response.text();
        msgDigitando.remove();
        adicionarMensagem('bot', resposta);
    } catch (e) {
        msgDigitando.remove();
        adicionarMensagem('bot', 'Erro ao consultar o servidor. Tente novamente.');
        console.error(e);
    }

    mensagens.scrollTop = mensagens.scrollHeight;
}

/*async function enviarMensagem() {
    const pergunta = inputPergunta.value.trim();
    if (!pergunta) return;

    adicionarMensagem('cliente', pergunta);
    inputPergunta.value = '';

    // Cria a bolha do bot vazia (vai sendo preenchida)
    const msgBot = adicionarMensagem('bot', '');
    let textoAcumulado = '';

    try {
        const response = await fetch('/api/chat/stream', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ pergunta })
        });

        const reader = response.body.getReader();
        const decoder = new TextDecoder('utf-8');
        let buffer = '';

        while (true) {
            const { done, value } = await reader.read();
            if (done) break;

            buffer += decoder.decode(value, { stream: true });

            // SSE: cada evento vem como "data:conteudo\n\n"
            const linhas = buffer.split('\n');
            buffer = linhas.pop(); // guarda a última linha incompleta

            for (const linha of linhas) {
                if (linha.startsWith('data:')) {
                    const token = linha.slice(5); // remove "data:"
                    textoAcumulado += token;
                    msgBot.textContent = textoAcumulado;
                    mensagens.scrollTop = mensagens.scrollHeight;
                }
            }
        }
    } catch (e) {
        msgBot.textContent = 'Erro ao consultar o servidor.';
        console.error(e);
    }

    mensagens.scrollTop = mensagens.scrollHeight;
}*/

function adicionarMensagem(tipo, texto) {
    const div = document.createElement('div');
    div.classList.add('msg', tipo);
    div.textContent = texto;
    mensagens.appendChild(div);
    mensagens.scrollTop = mensagens.scrollHeight;
    return div;
}

// Inicializa
carregarDados();