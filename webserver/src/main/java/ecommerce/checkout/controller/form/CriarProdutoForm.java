package ecommerce.checkout.controller.form;

import ecommerce.checkout.model.Produto;

final public class CriarProdutoForm {

    public String nome;
    public String descricao;
    public double preco;

    public Produto converteParaProduto() {
        return Produto.comNomeDescricaoPreco(nome, descricao, preco);
    }

}
