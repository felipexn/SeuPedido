package com.seupedido.Service;

import com.seupedido.DAO.ItemDAO;
import com.seupedido.DAO.PedidoDAO;
import com.seupedido.enums.StatusPedidoEnuns;
import com.seupedido.Model.Item;
import com.seupedido.Model.Pedido;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class CaixaService {

    private final PedidoDAO pedidoDao;
    private final ItemDAO itemDao;

    public CaixaService(PedidoDAO pedidoDao, ItemDAO itemDao) {
        this.pedidoDao = pedidoDao;
        this.itemDao = itemDao;
    }

    public void adicionarItemAoCardapio(Item item) {
        itemDao.create(item);
    }

    public void finalizarPedido(Pedido pedido) {
        pedido.setStatus(StatusPedidoEnuns.FINALIZADO);
        pedido.setData(LocalDateTime.now());
        if (pedido.getTotal() == null) {
            pedido.setTotal(BigDecimal.ZERO); // Só pra garantir
        }
        pedidoDao.update(pedido);
    }
}
