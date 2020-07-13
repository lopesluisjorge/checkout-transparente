package ecommerce.checkout.model;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotEmpty;

@Embeddable
public class Endereco {

    @NotEmpty
    @Column(name = "endereco_cep")
    private String cep;
    @NotEmpty
    @Column(name = "endereco_logradouro")
    private String logradouro;
    @Column(name = "endereco_numero")
    private String numero;
    @Column(name = "endereco_bairro")
    private String bairro;
    @Column(name = "endereco_cidade")
    private String cidade;
    @Column(name = "endereco_estado")
    private String estado;
    @Column(name = "endereco_complemento")
    private String complemento;

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        if (Objects.isNull(cep))
            return "";

        return String.format("%s, %s - %s, %s - %s, %s, %s", logradouro, numero, bairro, cidade, estado, cep, complemento);
    }

}
