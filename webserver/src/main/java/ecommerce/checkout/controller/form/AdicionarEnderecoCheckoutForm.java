package ecommerce.checkout.controller.form;

import ecommerce.checkout.model.Endereco;

public class AdicionarEnderecoCheckoutForm {

    public final String cep;
    public final String logradouro;
    public final String numero;
    public final String bairro;
    public final String cidade;
    public final String estado;
    public final String complemento;

    public AdicionarEnderecoCheckoutForm(String cep, String logradouro, String numero, String bairro, String cidade,
            String estado, String complemento) {

        this.cep = cep;
        this.logradouro = logradouro;
        this.numero = numero;
        this.bairro = bairro;
        this.cidade = cidade;
        this.estado = estado;
        this.complemento = complemento;
    }

    public Endereco converteParaEndereco() {
        Endereco endereco = new Endereco();
        endereco.setCep(cep);
        endereco.setLogradouro(logradouro);
        endereco.setNumero(numero);
        endereco.setBairro(bairro);
        endereco.setCidade(cidade);
        endereco.setEstado(estado);
        endereco.setComplemento(complemento);

        return endereco;
    }

}
