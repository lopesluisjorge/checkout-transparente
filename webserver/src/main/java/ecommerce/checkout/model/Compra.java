package ecommerce.checkout.model;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PreUpdate;
import javax.persistence.Transient;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
public class Compra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;
    @NotEmpty
    @OneToMany(mappedBy = "id.compra", cascade = CascadeType.ALL, orphanRemoval = true)
    private final Set<ItemCompra> itens;
    @NotNull
    @Enumerated(EnumType.STRING)
    private CompraStatus status;
    @Embedded
    private Endereco endereco;
    @ManyToOne
    @JoinColumn(name = "transportadora_id")
    private Transportadora transportadora;
    private TipoPagamento tipoPagamento;
    private String codigoRastreio;
    private Integer versao = 1;

    protected Compra() {
        this.itens = new HashSet<>();
    }

    public static Compra novaCompra(Cliente cliente, Set<ItemCompra> itens) {
        var compra = new Compra();
        compra.cliente = cliente;
        compra.status = CompraStatus.INICIO_CHECKOUT;
        itens.forEach(item -> item.getId().setCompra(compra));
        compra.itens.addAll(itens);

        return compra;
    }

    @PreUpdate
    protected void antesDeAtualizar() {
        atualizaVersao();
    }

    public Compra adicionar(ItemCompra produto) {
        this.itens.add(produto);
        return this;
    }

    public Long getId() {
        return id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    @Transient
    public BigDecimal getSubtotal() {
        var subtotal = BigDecimal.ZERO;

        for (ItemCompra item : itens) {
            var precoUnitario = item.getPreco();
            var quantidade = item.getQuantidade();

            var precoTotalProduto = precoUnitario.multiply(new BigDecimal(quantidade));

            subtotal = subtotal.add(precoTotalProduto);
        }

        return subtotal;
    }

    @Transient
    public BigDecimal total() {
        var subtotal = getSubtotal();

        var transportadoraOp = Optional.of(transportadora);
        var frete = BigDecimal.ZERO;

        if (transportadoraOp.isPresent()) {
            frete = transportadoraOp.get().getPreco();
        }

        return subtotal.add(frete);
    }

    public CompraStatus getStatus() {
        return status;
    }

    public void setStatus(CompraStatus status) {
        this.status = status;
    }

    public Set<ItemCompra> getItens() {
        return Collections.unmodifiableSet(itens);
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public Transportadora getTransportadora() {
        return transportadora;
    }

    public void setTransportadora(Transportadora transportadora) {
        this.transportadora = transportadora;
    }

    public String getCodigoRastreio() {
        return codigoRastreio;
    }

    public void setCodigoRastreio(String codigoRastreio) {
        this.codigoRastreio = codigoRastreio;
    }

    public Integer getVersao() {
        return versao;
    }

    public TipoPagamento getTipoPagamento() {
        return tipoPagamento;
    }

    public void setTipoPagamento(TipoPagamento tipoPagamento) {
        this.tipoPagamento = tipoPagamento;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Compra other = (Compra) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    private void atualizaVersao() {
        ++this.versao;
    }

}
