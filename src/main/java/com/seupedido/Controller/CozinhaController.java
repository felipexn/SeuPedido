package com.seupedido.Controller;

import com.seupedido.Model.ItemPedido;
import com.seupedido.Service.CozinhaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cozinha")
public class CozinhaController {

    private final CozinhaService cozinhaService;

    public CozinhaController(CozinhaService cozinhaService) {
        this.cozinhaService = cozinhaService;
    }

    // GET /cozinha - lista os itens pendentes de preparo
    @GetMapping
    public ResponseEntity<List<ItemPedido>> listarPendentes() {
        List<ItemPedido> pendentes = cozinhaService.listarPendentes();
        return ResponseEntity.ok(pendentes);
    }

    // POST /cozinha/{id}/pronto - marca o item como pronto
    @PostMapping("/{id}/pronto")
    public ResponseEntity<Void> marcarComoPronto(@PathVariable Long id) {
        cozinhaService.marcarComoPronto(id);
        return ResponseEntity.noContent().build();
    }
}
