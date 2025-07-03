package com.seupedido.Model;

import java.math.BigDecimal;

public class Item {
    private Long id;
    private String produto;
    private BigDecimal preco;
    private boolean precisaCozinha;

    public Item() { }

    public Item(Long id, String produto, BigDecimal preco, boolean precisaCozinha) {
        this.id = id;
        this.produto = produto;
        this.preco = preco;
        this.precisaCozinha = precisaCozinha;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getProduto() { return produto; }
    public void setProduto(String produto) { this.produto = produto; }

    public BigDecimal getPreco() { return preco; }
    public void setPreco(BigDecimal preco) { this.preco = preco; }

    public boolean isPrecisaCozinha() { return precisaCozinha; }
    public void setPrecisaCozinha(boolean precisaCozinha) { this.precisaCozinha = precisaCozinha; }
}
