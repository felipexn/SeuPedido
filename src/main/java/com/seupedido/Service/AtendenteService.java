package com.seupedido.Service;

import com.seupedido.DAO.ItemPedidoDAO;
import com.seupedido.enums.StatusItemEnuns;
import com.seupedido.Model.ItemPedido;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AtendenteService {

    private final ItemPedidoDAO itemPedidoDao;

    public AtendenteService(ItemPedidoDAO itemPedidoDao) {
        this.itemPedidoDao = itemPedidoDao;
    }

    public List<ItemPedido> listarProntosParaEntrega() {
        return itemPedidoDao.findReadyForAttendant();
    }

    public void marcarComoEntregue(Long idItemPedido) {
        itemPedidoDao.updateStatus(idItemPedido, StatusItemEnuns.ENTREGUE.name());
    }
}
