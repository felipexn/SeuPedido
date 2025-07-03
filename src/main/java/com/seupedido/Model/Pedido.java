package com.seupedido.Model;

import com.seupedido.enums.StatusPedidoEnuns;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class Pedido {
    private Long id;
    private LocalDateTime data;
    private BigDecimal total;
    private StatusPedidoEnuns status;
    private Long mesaId;
    private List<ItemPedido> itens;  // pode ser null até popular via DAO

    public Pedido() { }

    public Pedido(Long id, LocalDateTime data, BigDecimal total,
                  StatusPedidoEnuns status, Long mesaId) {
        this.id = id;
        this.data = data;
        this.total = total;
        this.status = status;
        this.mesaId = mesaId;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public LocalDateTime getData() { return data; }
    public void setData(LocalDateTime data) { this.data = data; }

    public BigDecimal getTotal() { return total; }
    public void setTotal(BigDecimal total) { this.total = total; }

    public StatusPedidoEnuns getStatus() { return status; }
    public void setStatus(StatusPedidoEnuns status) { this.status = status; }

    public Long getMesaId() { return mesaId; }
    public void setMesaId(Long mesaId) { this.mesaId = mesaId; }

    public List<ItemPedido> getItens() { return itens; }
    public void setItens(List<ItemPedido> itens) { this.itens = itens; }
}
