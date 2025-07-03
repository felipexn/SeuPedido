package com.seupedido.Controller;

import com.seupedido.Model.ItemPedido;
import com.seupedido.Model.Pedido;
import com.seupedido.Service.AtendenteService;
import com.seupedido.Service.PedidoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/atendente/view")
public class AtendenteViewController {

    private final AtendenteService atendenteService;
    private final PedidoService pedidoService;

    public AtendenteViewController(AtendenteService atendenteService,
                                   PedidoService pedidoService) {
        this.atendenteService = atendenteService;
        this.pedidoService = pedidoService;
    }

    /**
     * Mostra todos os itens prontos para entrega
     * GET /atendente/view
     */
    @GetMapping
    public String viewAtendente(Model model) {
        List<ItemPedido> prontos = atendenteService.listarProntosParaEntrega();
        model.addAttribute("prontos", prontos);
        return "atendente";
    }

    /**
     * Marca um item como entregue
     * POST /atendente/view/entregar/{id}
     */
    @PostMapping("/entregar/{id}")
    public String entregar(@PathVariable Long id) {
        atendenteService.marcarComoEntregue(id);
        return "redirect:/atendente/view";
    }

    /**
     * Exibe o pedido aberto de uma mesa específica
     * GET /atendente/view/mesas/{mesaId}
     */
    @GetMapping("/mesas/{mesaId}")
    public String verPedidoMesa(@PathVariable Long mesaId, Model model) {
        Pedido pedido = pedidoService.buscarPedidoAbertoPorMesa(mesaId);
        if (pedido == null) {
            model.addAttribute("mensagem", "Nenhum pedido aberto para esta mesa.");
            model.addAttribute("mesaId", mesaId);
            return "atendente-mesa";
        }

        List<ItemPedido> itens = pedidoService.listarItensDoPedido(pedido.getId());
        model.addAttribute("itens", itens);
        model.addAttribute("mesaId", mesaId);
        model.addAttribute("pedidoId", pedido.getId());
        return "atendente-mesa";
    }

    /**
     * Permite ao atendente adicionar um item em nome da mesa
     * POST /atendente/view/mesas/{mesaId}/adicionar/{itemId}
     */
    @PostMapping("/mesas/{mesaId}/adicionar/{itemId}")
    public String adicionarItemMesa(@PathVariable Long mesaId,
                                    @PathVariable Long itemId) {
        pedidoService.adicionarItemAoPedido(mesaId, itemId);
        return "redirect:/atendente/view/mesas/" + mesaId;
    }
}
