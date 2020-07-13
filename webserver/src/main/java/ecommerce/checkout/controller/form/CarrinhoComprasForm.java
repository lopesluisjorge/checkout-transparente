package ecommerce.checkout.controller.form;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class CarrinhoComprasForm {

    public Set<ItemCarrinhoComprasForm> produtos;

    public CarrinhoComprasForm() {
        produtos = new HashSet<>();
    }

    public Map<Long, Integer> converteParaMapa() {
        var map = new HashMap<Long, Integer>();

        produtos.forEach(item -> map.put(item.produtoId, item.quantidade));

        return map;
    }

}

class ItemCarrinhoComprasForm {

    public Long produtoId;
    public Integer quantidade;

}