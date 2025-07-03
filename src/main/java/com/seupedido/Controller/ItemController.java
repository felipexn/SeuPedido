package com.seupedido.Controller;

import com.seupedido.Model.Item;
import com.seupedido.Service.ItemService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/itens")
public class ItemController {
    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    // GET /itens - lista todos os itens do cardápio
    @GetMapping
    public ResponseEntity<List<Item>> listarTodos() {
        List<Item> itens = itemService.listarTodos();
        return ResponseEntity.ok(itens);
    }

    // GET /itens/{id} - busca item por id
    @GetMapping("/{id}")
    public ResponseEntity<Item> buscarPorId(@PathVariable Long id) {
        Item item = itemService.buscarPorId(id);
        if (item == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(item);
    }

    // POST /itens - adiciona novo item ao cardápio
    @PostMapping
    public ResponseEntity<Long> criarItem(@RequestBody Item item) {
        Long novoId = itemService.criarItem(item);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoId);
    }

    // PUT /itens/{id} - atualiza item existente
    @PutMapping("/{id}")
    public ResponseEntity<Void> atualizarItem(@PathVariable Long id, @RequestBody Item item) {
        itemService.atualizarItem(id, item);
        return ResponseEntity.noContent().build();
    }

    // DELETE /itens/{id} - remove item do cardápio
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarItem(@PathVariable Long id) {
        itemService.removerItem(id);
        return ResponseEntity.noContent().build();
    }
}
