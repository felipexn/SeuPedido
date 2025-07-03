package com.seupedido.Service;

import com.seupedido.DAO.ItemPedidoDAO;
import com.seupedido.enums.StatusItemEnuns;
import com.seupedido.Model.ItemPedido;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CozinhaService {

    private final ItemPedidoDAO itemPedidoDao;

    public CozinhaService(ItemPedidoDAO itemPedidoDao) {
        this.itemPedidoDao = itemPedidoDao;
    }

    public List<ItemPedido> listarPendentes() {
        return itemPedidoDao.findPendingKitchenJobs();
    }

    public void marcarComoPronto(Long idItemPedido) {
        itemPedidoDao.updateStatus(idItemPedido, StatusItemEnuns.PRONTO.name());
    }

}
