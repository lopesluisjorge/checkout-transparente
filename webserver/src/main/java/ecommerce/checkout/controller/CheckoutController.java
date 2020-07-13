package ecommerce.checkout.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashSet;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ecommerce.checkout.controller.dto.CompraDto;
import ecommerce.checkout.controller.dto.FinalizacaoCompraDto;
import ecommerce.checkout.controller.form.AdicionarEnderecoCheckoutForm;
import ecommerce.checkout.controller.form.AdicionarPagamentoCheckoutForm;
import ecommerce.checkout.controller.form.AdicionarTransportadoraCheckoutForm;
import ecommerce.checkout.controller.form.CarrinhoComprasForm;
import ecommerce.checkout.model.Compra;
import ecommerce.checkout.model.CompraStatus;
import ecommerce.checkout.model.ItemCompra;
import ecommerce.checkout.model.Produto;
import ecommerce.checkout.model.TipoPagamento;
import ecommerce.checkout.repository.ClienteRepository;
import ecommerce.checkout.repository.CompraRepository;
import ecommerce.checkout.repository.ProdutoRepository;
import ecommerce.checkout.repository.TransportadoraRepository;

@RestController
@RequestMapping("/ws/checkout")
class CheckoutController {

    private final Logger log;
    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private ProdutoRepository produtoRepository;
    @Autowired
    private CompraRepository compraRepository;
    @Autowired
    private TransportadoraRepository transportadoraRepository;

    public CheckoutController() {
        log = LoggerFactory.getLogger(this.getClass());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CompraDto> statusCheckout(@PathVariable Long id) {
        var compraOp = compraRepository.findById(id);

        if (compraOp.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        var compra = compraOp.get();

        return ResponseEntity.ok(CompraDto.aPartirDe(compra));
    }

    @PostMapping
    @Transactional
    public ResponseEntity<CompraDto> iniciarCheckout(@RequestBody CarrinhoComprasForm form, HttpServletRequest request)
            throws URISyntaxException {
        var cliente = clienteRepository.findById(1l).get();
        var mapaProdutosQuantidade = form.converteParaMapa();

        var idsProdutos = mapaProdutosQuantidade.keySet();

        var produtos = produtoRepository.findAllById(idsProdutos);

        var itens = new HashSet<ItemCompra>();

        for (Produto produto : produtos) {
            var itemCompra = new ItemCompra();
            var idItemCompra = itemCompra.getId();
            idItemCompra.setProduto(produto);
            var quantidade = mapaProdutosQuantidade.get(produto.getId());

            itemCompra.setQuantidade(quantidade);

            itens.add(itemCompra);
        }

        var compra = Compra.novaCompra(cliente, itens);

        compraRepository.save(compra);

        var compraDto = CompraDto.aPartirDe(compra);

        log.info("Cliente {} iniciando checkout {}", cliente.getId(), compraDto);

        var urlAtual = request.getRequestURL().toString();
        var uri = new URI(urlAtual + "/" + compra.getId());

        return ResponseEntity.created(uri).body(compraDto);
    }

    @PatchMapping("/{idCompra}/endereco")
    @Transactional
    public ResponseEntity<CompraDto> adicionarendereco(@PathVariable Long idCompra,
            @Valid @RequestBody AdicionarEnderecoCheckoutForm form) {
        var endereco = form.converteParaEndereco();

        var compraOp = compraRepository.findById(idCompra);
        if (compraOp.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var compra = compraOp.get();

        compra.setEndereco(endereco);
        compra.setStatus(CompraStatus.SELECAO_ENDERECO);

        return ResponseEntity.ok(CompraDto.aPartirDe(compra));
    }

    @PatchMapping("/{idCompra}/transportadora")
    @Transactional
    public ResponseEntity<CompraDto> adicionarTransporadora(@PathVariable Long idCompra,
            @Valid @RequestBody AdicionarTransportadoraCheckoutForm form) {
        var compraOp = compraRepository.findById(idCompra);
        if (compraOp.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var compra = compraOp.get();

        var transprotadoraOp = transportadoraRepository.findById(form.transportadoraId);
        if (transprotadoraOp.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        var transportadora = transprotadoraOp.get();

        compra.setTransportadora(transportadora);
        compra.setStatus(CompraStatus.SELECAO_FRETE);

        return ResponseEntity.ok(CompraDto.aPartirDe(compra));
    }

    @PatchMapping("/{idCompra}/forma-pagamento")
    @Transactional
    public ResponseEntity<CompraDto> adicionarPagamento(@PathVariable Long idCompra,
            @Valid @RequestBody AdicionarPagamentoCheckoutForm form) {
        var compraOp = compraRepository.findById(idCompra);
        if (compraOp.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var compra = compraOp.get();

        var tipoPagamento = form.tipoPagamento;

        compra.setTipoPagamento(TipoPagamento.valueOf(tipoPagamento));
        compra.setStatus(CompraStatus.FORMA_DE_PAGAMENTO);

        return ResponseEntity.ok(CompraDto.aPartirDe(compra));
    }

    @PatchMapping("/{idCompra}/confirmacao-pagamento")
    @Transactional
    public ResponseEntity<FinalizacaoCompraDto> confirmarPagamento(@PathVariable Long idCompra) {
        var compraOp = compraRepository.findById(idCompra);
        if (compraOp.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var compra = compraOp.get();

        compra.setStatus(CompraStatus.PAGO);
        compra.setCodigoRastreio(UUID.randomUUID().toString());

        return ResponseEntity.ok(FinalizacaoCompraDto.aPartirDe(compra));
    }

}
