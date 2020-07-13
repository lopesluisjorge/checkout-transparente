package ecommerce.checkout.model;

import java.math.BigDecimal;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "item_compra")
public class ItemCompra {

    @EmbeddedId
    private ItemCompraId id = new ItemCompraId();
    @NotNull
    @Min(1)
    private Integer quantidade;
    private BigDecimal preco;

    @PrePersist
    private void definePreco() {
        this.preco = id.getProduto().getPreco();
    }

    public ItemCompraId getId() {
        return id;
    }

    public void setId(ItemCompraId id) {
        this.id = id;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ItemCompra other = (ItemCompra) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

}
