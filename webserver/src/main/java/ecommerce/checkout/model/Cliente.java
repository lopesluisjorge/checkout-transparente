package ecommerce.checkout.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.br.CPF;

@Entity
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    @Size(min = 3)
    private String nome;
    @NotNull
    @Email
    @Column(unique = true)
    private String email;
    @Pattern(regexp = "[1-9]{2}9?[0-9]{8}")
    private String telefone;
    @NotNull
    @Size(min = 6)
    private String senha;
    @CPF
    private String cpf;

    protected Cliente() {

    }

    public Cliente(String nome, String email, String senha) {
        setNome(nome);
        setEmail(email);
        setSenha(senha);
    }

    public static Cliente comNomeEmailSenha(String nome, String email, String senha) {
        return new Cliente(nome, email, senha);
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

}
