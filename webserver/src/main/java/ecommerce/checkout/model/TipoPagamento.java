package ecommerce.checkout.model;

public enum TipoPagamento {

    CARTAO_DE_CREDITO("CARTAO_DE_CREDITO"),
    BOLETO_BANCARIO("BOLETO_BANCARIO"),
    EM_CHECKOUT("EM_CHECKOUT");

    private final String tipoPagamento;

    private TipoPagamento(String tipo) {
        this.tipoPagamento = tipo;
    }

    public String getTipoPagamento() {
        return tipoPagamento;
    }
}
