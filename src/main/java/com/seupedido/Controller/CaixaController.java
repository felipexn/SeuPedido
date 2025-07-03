package com.seupedido.Controller;

import com.seupedido.Model.Item;
import com.seupedido.Model.Pedido;
import com.seupedido.Service.CaixaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/caixa")
public class CaixaController {

    private final CaixaService caixaService;

    public CaixaController(CaixaService caixaService) {
        this.caixaService = caixaService;
    }

    // POST /caixa/cardapio - adiciona novo item ao cardápio
    @PostMapping("/cardapio")
    public ResponseEntity<Void> adicionarItem(@RequestBody Item item) {
        caixaService.adicionarItemAoCardapio(item);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    // POST /caixa/finalizar - finaliza pedido manualmente
    @PostMapping("/finalizar")
    public ResponseEntity<Void> finalizarPedido(@RequestBody Pedido pedido) {
        caixaService.finalizarPedido(pedido);
        return ResponseEntity.noContent().build();
    }
}
