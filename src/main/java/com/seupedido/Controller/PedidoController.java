package com.seupedido.Controller;

import com.seupedido.Model.ItemPedido;
import com.seupedido.Model.Pedido;
import com.seupedido.Service.PedidoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    private final PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    // POST /mesas/{idMesa}/pedidos/{idItem} - adiciona um item ao pedido da mesa
    @PostMapping("/mesas/{idMesa}/pedidos/{idItem}")
    public ResponseEntity<Pedido> adicionarItem(@PathVariable Long idMesa, @PathVariable Long idItem) {
        Pedido pedido = pedidoService.adicionarItemAoPedido(idMesa, idItem);
        return ResponseEntity.status(HttpStatus.CREATED).body(pedido);
    }

    // GET /pedidos/{id} - lista todos os itens de um pedido
    @GetMapping("/{id}")
    public ResponseEntity<List<ItemPedido>> listarItens(@PathVariable Long id) {
        List<ItemPedido> itens = pedidoService.listarItensDoPedido(id);
        return ResponseEntity.ok(itens);
    }

    // POST /pedidos/{id}/finalizar - finaliza o pedido
    @PostMapping("/{id}/finalizar")
    public ResponseEntity<Void> finalizar(@PathVariable Long id) {
        pedidoService.finalizarPedido(id);
        return ResponseEntity.noContent().build();
    }
}
