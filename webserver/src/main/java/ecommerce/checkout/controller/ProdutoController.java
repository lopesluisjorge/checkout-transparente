package ecommerce.checkout.controller;

import java.net.URI;
import java.net.URISyntaxException;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ecommerce.checkout.controller.dto.ProdutoDto;
import ecommerce.checkout.controller.form.CriarProdutoForm;
import ecommerce.checkout.repository.ProdutoRepository;

@RestController
@RequestMapping("/ws/produtos")
class ProdutoController {

    private final Logger log;

    private final ProdutoRepository produtoRepository;
    
    public ProdutoController(ProdutoRepository produtoRepository) {
        log = LoggerFactory.getLogger(this.getClass());
        this.produtoRepository = produtoRepository;
    }

    @GetMapping
    public ResponseEntity<Page<ProdutoDto>> listar(Pageable pagina) {
        var produtos = produtoRepository.findAll(pagina);

        var paginaDto = produtos.map(produto -> ProdutoDto.de(produto));

        return ResponseEntity.ok(paginaDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProdutoDto> buscarPorId(@PathVariable Long id) {
        var produtoOp = produtoRepository.findById(id);

        if (produtoOp.isEmpty()) {
            log.info("Produto com id {} não encontrado", id);
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(ProdutoDto.de(produtoOp.get()));
    }

    @PostMapping
    @Transactional
    public ResponseEntity<ProdutoDto> cadastrarProduto(@RequestBody CriarProdutoForm dto, HttpServletRequest req)
            throws URISyntaxException {
        var produto = dto.converteParaProduto();

        produtoRepository.save(produto);

        log.info("Cadastrando novo produto {}", produto);

        var urlAtual = req.getRequestURL().toString();
        var uri = new URI(urlAtual + "/" + produto.getId());

        return ResponseEntity.created(uri).body(ProdutoDto.de(produto));
    }
}
