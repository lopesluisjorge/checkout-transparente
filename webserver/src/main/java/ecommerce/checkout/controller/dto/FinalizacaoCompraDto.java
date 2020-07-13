package ecommerce.checkout.controller.dto;

import ecommerce.checkout.model.Compra;

public class FinalizacaoCompraDto {

    public Long numeroPedido;
    public String codigoRastreio;

    public static FinalizacaoCompraDto aPartirDe(Compra compra) {
        var dto = new FinalizacaoCompraDto();

        dto.numeroPedido = compra.getId();
        dto.codigoRastreio = compra.getCodigoRastreio();

        return dto;
    }

}
