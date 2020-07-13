package ecommerce.checkout.controller.form;

import ecommerce.checkout.model.Cliente;

public class RegitroClienteForm {

    public String nome;
    public String email;
    public String senha;

    public Cliente converteParaCliente() {
        return Cliente.comNomeEmailSenha(nome, email, senha);
    }

}
