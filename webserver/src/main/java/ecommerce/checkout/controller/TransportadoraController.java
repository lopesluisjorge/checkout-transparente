package ecommerce.checkout.controller;

import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ecommerce.checkout.controller.dto.TransportadoraDto;
import ecommerce.checkout.controller.form.CriarTransportadoraForm;
import ecommerce.checkout.model.Transportadora;
import ecommerce.checkout.repository.TransportadoraRepository;

@RestController
@RequestMapping("/ws/transportadoras")
class TransportadoraController {

    private final Logger log;
    private final TransportadoraRepository transportadoraRepository;

    public TransportadoraController(TransportadoraRepository transportadoraRepository) {
        log = LoggerFactory.getLogger(this.getClass());
        this.transportadoraRepository = transportadoraRepository;
    }

    @GetMapping
    public List<TransportadoraDto> todasTransportadoras() {
        var transportadoras = transportadoraRepository.findAll();

        var listaDtos = transportadoras.stream().map(t -> TransportadoraDto.aPartirDe(t)).collect(Collectors.toList());

        return listaDtos;
    }
    
    @PostMapping
    @Transactional
    public ResponseEntity<TransportadoraDto> cadastrarTransportadora(@RequestBody CriarTransportadoraForm dto, HttpServletRequest req)
            throws URISyntaxException {
        var transportadora = new Transportadora();
        transportadora.setNome(dto.nome);
        transportadora.setPreco(new BigDecimal(dto.preco));
        
        transportadoraRepository.save(transportadora);

        log.info("Cadastrando nova transportadora {}", transportadora);

        var urlAtual = req.getRequestURL().toString();
        var uri = new URI(urlAtual + "/" + transportadora.getId());

        return ResponseEntity.created(uri).body(TransportadoraDto.aPartirDe(transportadora));
    }

}
