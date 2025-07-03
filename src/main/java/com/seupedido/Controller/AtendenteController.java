package com.seupedido.Controller;

import com.seupedido.Model.ItemPedido;
import com.seupedido.Model.Pedido;
import com.seupedido.Service.AtendenteService;
import com.seupedido.Service.PedidoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/atendente")
public class AtendenteController {

    private final AtendenteService atendenteService;

    private final PedidoService pedidoService;

    public AtendenteController(AtendenteService atendenteService, PedidoService pedidoService) {
        this.atendenteService = atendenteService;
        this.pedidoService = pedidoService;
    }

    // GET /atendente - lista os itens prontos para entrega
    @GetMapping
    public ResponseEntity<List<ItemPedido>> listarProntos() {
        List<ItemPedido> prontos = atendenteService.listarProntosParaEntrega();
        return ResponseEntity.ok(prontos);
    }
    // GET /atendente/mesas/{idMesa} - lista os itens do pedido atual da mesa
    @GetMapping("/mesas/{idMesa}")
    public ResponseEntity<List<ItemPedido>> verPedidoMesa(@PathVariable Long idMesa) {
        Pedido pedido = pedidoService.buscarPedidoAbertoPorMesa(idMesa);
        if (pedido == null) {
            return ResponseEntity.notFound().build();
        }
        List<ItemPedido> itens = pedidoService.listarItensDoPedido(pedido.getId());
        return ResponseEntity.ok(itens);
    }

    // POST /atendente/mesas/{idMesa}/adicionar/{idItem}
    @PostMapping("/mesas/{idMesa}/adicionar/{idItem}")
    public ResponseEntity<Pedido> adicionarItemMesa(@PathVariable Long idMesa, @PathVariable Long idItem) {
        Pedido pedido = pedidoService.adicionarItemAoPedido(idMesa, idItem);
        return ResponseEntity.status(HttpStatus.CREATED).body(pedido);
    }


    // POST /atendente/{id}/entregar - marca o item como entregue
    @PostMapping("/{id}/entregar")
    public ResponseEntity<Void> entregar(@PathVariable Long id) {
        atendenteService.marcarComoEntregue(id);
        return ResponseEntity.noContent().build();
    }
}
