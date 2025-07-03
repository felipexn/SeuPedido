package com.seupedido.Model;

import com.seupedido.enums.StatusItemEnuns;



public class ItemPedido {
    private Long id;
    private Long pedidoId;
    private Long itemId;
    private int quantidade;
    private StatusItemEnuns status;
    // Para facilitar, você pode opcionalmente ter referências:
    // private Item item;
    // private Pedido pedido;

    public ItemPedido() { }

    public ItemPedido(Long id, Long pedidoId, Long itemId,
                      int quantidade, StatusItemEnuns  status) {
        this.id = id;
        this.pedidoId = pedidoId;
        this.itemId = itemId;
        this.quantidade = quantidade;
        this.status = status;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getPedidoId() { return pedidoId; }
    public void setPedidoId(Long pedidoId) { this.pedidoId = pedidoId; }

    public Long getItemId() { return itemId; }
    public void setItemId(Long itemId) { this.itemId = itemId; }

    public int getQuantidade() { return quantidade; }
    public void setQuantidade(int quantidade) { this.quantidade = quantidade; }

    public StatusItemEnuns getStatus() { return status; }
    public void setStatus(StatusItemEnuns status) { this.status = status; }
}
