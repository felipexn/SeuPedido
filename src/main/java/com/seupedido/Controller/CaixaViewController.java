package com.seupedido.Controller;

import com.seupedido.Model.Item;
import com.seupedido.Model.Pedido;
import com.seupedido.Service.CaixaService;
import com.seupedido.Service.ItemService;
import com.seupedido.Service.PedidoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/caixa/view")
public class CaixaViewController {

    private final CaixaService caixaService;
    private final ItemService itemService;
    private final PedidoService pedidoService;

    public CaixaViewController(CaixaService caixaService,
                               ItemService itemService,
                               PedidoService pedidoService) {
        this.caixaService = caixaService;
        this.itemService = itemService;
        this.pedidoService = pedidoService;
    }

    /**
     * Exibe a página de gerenciamento do caixa
     * GET /caixa/view
     */


    /**
     * Adiciona um item ao cardápio via formulário de view
     * POST /caixa/view/cardapio
     */
    @PostMapping("/cardapio")
    public String adicionarItemAoCardapio(@ModelAttribute Item item) {
        caixaService.adicionarItemAoCardapio(item);
        return "redirect:/caixa/view";
    }

    /**
     * Finaliza um pedido (e libera a mesa) via view
     * POST /caixa/view/finalizar/{pedidoId}
     */
    @GetMapping
    public String viewCaixa(Model model) {
        model.addAttribute("newItem", new Item());
        model.addAttribute("cardapio", itemService.listarTodos());
        model.addAttribute("pedidosAbertos", pedidoService.listarPedidosAbertos()); // <--- novo
        return "caixa";
    }

    @PostMapping("/finalizar/{id}")
    public String finalizarPedido(@PathVariable Long id) {
        pedidoService.finalizarPedido(id);
        return "redirect:/caixa/view";

    }
}
