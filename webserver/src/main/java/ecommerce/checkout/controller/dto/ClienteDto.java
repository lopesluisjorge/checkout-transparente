package ecommerce.checkout.controller.dto;

import ecommerce.checkout.model.Cliente;

public class ClienteDto {

    public final Long id;
    public final String nome;
    public final String email;

    public static ClienteDto de(Cliente cliente) {
        return new ClienteDto(cliente.getId(), cliente.getNome(), cliente.getEmail());
    }

    public ClienteDto(Long id, String nome, String email) {
        this.id = id;
        this.nome = nome;
        this.email = email;
    }

}
