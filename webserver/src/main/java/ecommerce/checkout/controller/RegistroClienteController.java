package ecommerce.checkout.controller;

import java.net.URI;
import java.net.URISyntaxException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ecommerce.checkout.controller.dto.ClienteDto;
import ecommerce.checkout.controller.form.RegitroClienteForm;
import ecommerce.checkout.repository.ClienteRepository;

@RestController
@RequestMapping("/ws/registro")
class RegistroClienteController {

    private final Logger log;
    private final BCryptPasswordEncoder encoder;
    private final ClienteRepository clienteRepository;

    public RegistroClienteController(ClienteRepository clienteRepository, BCryptPasswordEncoder encoder) {
        log = LoggerFactory.getLogger(this.getClass());
        this.encoder = encoder;
        this.clienteRepository = clienteRepository;
    }

    @PostMapping
    @Transactional
    public ResponseEntity<ClienteDto> registrar(@Valid @RequestBody RegitroClienteForm form, HttpServletRequest request)
            throws URISyntaxException {
        var cliente = form.converteParaCliente();

        cliente.setSenha(encoder.encode(form.senha));

        clienteRepository.save(cliente);

        var urlAtual = request.getRequestURL().toString();
        var uri = new URI(urlAtual + "/" + cliente.getId());

        var dto = ClienteDto.de(cliente);

        log.info("Adicionando novo cliente {}", dto);

        return ResponseEntity.created(uri).body(dto);
    }

}
