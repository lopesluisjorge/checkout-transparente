package ecommerce.checkout.model;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Embeddable
public class ItemCompraId implements Serializable {

    private static final long serialVersionUID = 7024171706638237238L;

    @ManyToOne
    @JoinColumn(name = "produto_id")
    private Produto produto;

    @ManyToOne
    @JoinColumn(name = "compra_id")
    private Compra compra;

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public Compra getCompra() {
        return compra;
    }

    public void setCompra(Compra compra) {
        this.compra = compra;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((compra == null) ? 0 : compra.hashCode());
        result = prime * result + ((produto == null) ? 0 : produto.hashCode());
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
        ItemCompraId other = (ItemCompraId) obj;
        if (compra == null) {
            if (other.compra != null)
                return false;
        } else if (!compra.equals(other.compra))
            return false;
        if (produto == null) {
            if (other.produto != null)
                return false;
        } else if (!produto.equals(other.produto))
            return false;
        return true;
    }

}
