package com.seupedido.Controller;

import com.seupedido.Model.Item;
import com.seupedido.Model.ItemPedido;
import com.seupedido.Model.Mesa;
import com.seupedido.Model.Pedido;
import com.seupedido.Service.ItemService;
import com.seupedido.Service.MesaService;
import com.seupedido.Service.PedidoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/mesas/{mesaId}")
public class MesaViewController {

    private final MesaService mesaService;
    private final ItemService itemService;
    private final PedidoService pedidoService;

    public MesaViewController(MesaService mesaService,
                              ItemService itemService,
                              PedidoService pedidoService) {
        this.mesaService = mesaService;
        this.itemService = itemService;
        this.pedidoService = pedidoService;
    }

    @GetMapping("/view")
    public String viewMesa(@PathVariable Long mesaId, Model model) {
        Mesa mesa = mesaService.buscarPorId(mesaId);



        Pedido pedido = pedidoService.buscarPedidoAbertoPorMesa(mesaId);

        if (!mesa.isOcupada()) {
            mesaService.reservar(mesaId);
        }

        // cardápio completo
        List<Item> itens = itemService.listarTodos();

        // itens do pedido
        List<ItemPedido> itensPedido = pedido != null
                ? pedidoService.listarItensDoPedido(pedido.getId())
                : List.of();

        model.addAttribute("mesaId", mesaId);
        model.addAttribute("itens", itens);
        model.addAttribute("itensPedido", itensPedido);
        model.addAttribute("pedidoId", pedido != null ? pedido.getId() : null);

        return "mesa";
    }


    @PostMapping("/adicionar/{itemId}")
    public String adicionarItem(@PathVariable Long mesaId,
                                @PathVariable Long itemId) {
        pedidoService.adicionarItemAoPedido(mesaId, itemId);
        return "redirect:/mesas/" + mesaId + "/view";
    }
}