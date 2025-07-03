// PedidoDao.java
package com.seupedido.DAO;

import com.seupedido.Model.Pedido;
import java.util.List;

public interface PedidoDAO {
    Pedido findById(Long id);
    List<Pedido> findByMesaId(Long mesaId);
    Pedido findAbertoByMesa(Long idMesa);
    void update(Pedido pedido);
    Long create(Pedido pedido);
    void updateTotal(Long pedidoId, java.math.BigDecimal total);
    void updateStatus(Long pedidoId, String status);
}
