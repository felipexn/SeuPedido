package com.seupedido.Service;

import com.seupedido.DAO.ItemDAO;
import com.seupedido.DAO.ItemPedidoDAO;
import com.seupedido.DAO.MesaDAO;
import com.seupedido.DAO.PedidoDAO;
import com.seupedido.enums.StatusItemEnuns;
import com.seupedido.enums.StatusPedidoEnuns;
import com.seupedido.Model.Item;
import com.seupedido.Model.ItemPedido;
import com.seupedido.Model.Mesa;
import com.seupedido.Model.Pedido;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class PedidoService {
    private final PedidoDAO pedidoDao;
    private final MesaDAO mesaDao;
    private final ItemDAO itemDao;
    private final ItemPedidoDAO itemPedidoDao;

    public PedidoService(PedidoDAO pedidoDao, MesaDAO mesaDao, ItemDAO itemDao, ItemPedidoDAO itemPedidoDao) {
        this.pedidoDao = pedidoDao;
        this.mesaDao = mesaDao;
        this.itemDao = itemDao;
        this.itemPedidoDao = itemPedidoDao;
    }

    public List<Pedido> listarPedidosAbertos() {
        return pedidoDao.findByStatus(StatusPedidoEnuns.ABERTO);
    }


    public Pedido adicionarItemAoPedido(Long idMesa, Long idItem) {
        Mesa mesa = mesaDao.findById(idMesa);
        if (!mesa.isOcupada()) {
            throw new IllegalStateException("Mesa não está ocupada.");
        }

        Pedido pedido = pedidoDao.findAbertoByMesa(idMesa);
        if (pedido == null) {
            pedido = new Pedido();
            pedido.setMesaId(idMesa);
            pedido.setStatus(StatusPedidoEnuns.ABERTO);
            pedido.setTotal(BigDecimal.valueOf(0.0)); // 🔥 inicia com zero
            Long idPedido = pedidoDao.create(pedido);
            pedido.setId(idPedido);
        }

        Item item = itemDao.findById(idItem);

        // 🔥 Cria o itemPedido
        ItemPedido itemPedido = new ItemPedido();
        itemPedido.setPedidoId(pedido.getId());
        itemPedido.setItemId(item.getId());
        itemPedido.setQuantidade(1); // define a quantidade como 1 por padrão
        itemPedido.setStatus(item.isPrecisaCozinha() ? StatusItemEnuns.PENDENTE : StatusItemEnuns.PRONTO);
        itemPedidoDao.create(itemPedido);

        // 🔥 Atualiza o total do pedido
        BigDecimal novoTotal = pedido.getTotal().add(item.getPreco());
        pedido.setTotal(novoTotal);
        pedidoDao.update(pedido);

        return pedido;
    }
    public Pedido buscarPedidoAbertoPorMesa(Long idMesa) {
        return pedidoDao.findAbertoByMesa(idMesa);
    }


    public List<ItemPedido> listarItensDoPedido(Long idPedido) {
        return itemPedidoDao.findByPedidoId(idPedido);
    }

    public void finalizarPedido(Long idPedido) {
        Pedido pedido = pedidoDao.findById(idPedido);
        pedido.setStatus(StatusPedidoEnuns.FINALIZADO);
        pedidoDao.update(pedido);

        Mesa mesa = mesaDao.findById(pedido.getMesaId());
        mesa.setOcupada(false);
        mesaDao.update(mesa);
    }
    public Pedido findById(Long id) {
        return pedidoDao.findById(id);
    }
}
