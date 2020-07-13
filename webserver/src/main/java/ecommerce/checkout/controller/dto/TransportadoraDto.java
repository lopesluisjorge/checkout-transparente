package ecommerce.checkout.controller.dto;

import ecommerce.checkout.model.Transportadora;

public class TransportadoraDto {

    public final Long id;
    public final String nome;
    public final Double preco;

    public TransportadoraDto(Long id, String nome, Double preco) {
        this.id = id;
        this.nome = nome;
        this.preco = preco;
    }

    public static TransportadoraDto aPartirDe(Transportadora transportadora) {
        return new TransportadoraDto(transportadora.getId(), transportadora.getNome(),
                transportadora.getPreco().doubleValue());
    }

}
