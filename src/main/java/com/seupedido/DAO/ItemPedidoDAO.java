// ItemPedidoDao.java
package com.seupedido.DAO;

import com.seupedido.Model.ItemPedido;
import java.util.List;

public interface ItemPedidoDAO {
    ItemPedido findById(Long id);
    List<ItemPedido> findByPedidoId(Long pedidoId);
    List<ItemPedido> findPendingKitchenJobs();
    List<ItemPedido> findReadyForAttendant();
    Long create(ItemPedido ip);
    void updateStatus(Long idItemPedido, String status);
    void updateQuantity(Long idItemPedido, int quantidade);
}
