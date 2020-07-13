package ecommerce.checkout.controller.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import ecommerce.checkout.model.Compra;
import ecommerce.checkout.model.CompraStatus;
import ecommerce.checkout.model.Endereco;
import ecommerce.checkout.model.ItemCompra;
import ecommerce.checkout.model.TipoPagamento;
import ecommerce.checkout.model.Transportadora;

public class CompraDto {

    public final Long id;
    public final ClienteDto cliente;
    public final List<ItemCompraDtoInternal> itens;
    public final CompraStatus status;
    public final String endereco;
    public final String transportadora;
    public final String tipoPagaamnto;

    public CompraDto(Long id, ClienteDto cliente, CompraStatus status, String endereco, String transportadora,
            String tipoPagamento) {
        this.id = id;
        this.cliente = cliente;
        this.itens = new ArrayList<>();
        this.status = status;
        this.endereco = endereco;
        this.transportadora = transportadora;
        this.tipoPagaamnto = tipoPagamento;
    }

    public static CompraDto aPartirDe(Compra compra) {
        var enderecoOp = Optional.ofNullable(compra.getEndereco());
        var endereco = enderecoOp.orElse(new Endereco());

        var transportadoraOp = Optional.ofNullable(compra.getTransportadora());
        var transportadora = transportadoraOp.orElse(new Transportadora());

        var tipoPagamentoOp = Optional.ofNullable(compra.getTipoPagamento());
        var tipoPagamento = tipoPagamentoOp.orElse(TipoPagamento.EM_CHECKOUT);

        var dto = new CompraDto(compra.getId(), ClienteDto.de(compra.getCliente()), compra.getStatus(),
                endereco.toString(), transportadora.toString(), tipoPagamento.toString());

        for (ItemCompra item : compra.getItens()) {
            var produtoId = item.getId().getProduto().getId();
            var nomeProduto = item.getId().getProduto().getNome();
            var quantidade = item.getQuantidade();
            var preco = item.getPreco().doubleValue();
            var itemDto = new ItemCompraDtoInternal(produtoId, nomeProduto, quantidade, preco);
            dto.itens.add(itemDto);
        }

        return dto;
    }

}

class ItemCompraDtoInternal {

    public final Long idProduto;
    public final String nomeProduto;
    public final Integer quantidade;
    public final Double preco;

    public ItemCompraDtoInternal(Long idProduto, String nomeProduto, Integer quantidade, Double preco) {
        this.idProduto = idProduto;
        this.nomeProduto = nomeProduto;
        this.quantidade = quantidade;
        this.preco = preco;
    }

}
