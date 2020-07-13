package ecommerce.checkout.controller.dto;

import ecommerce.checkout.model.Produto;

final public class ProdutoDto {

    public Long id;
    public String nome;
    public String descricao;
    public double preco;

    public static ProdutoDto de(Produto produto) {
        var dto = new ProdutoDto();
        dto.id = produto.getId();
        dto.nome = produto.getNome();
        dto.descricao = produto.getDescricao();
        dto.preco = produto.getPreco().doubleValue();

        return dto;
    }

}
