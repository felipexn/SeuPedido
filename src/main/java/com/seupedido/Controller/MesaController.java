package com.seupedido.Controller;

import com.seupedido.Model.Mesa;
import com.seupedido.Service.MesaService;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/mesas")
public class MesaController {

    private final MesaService mesaService;

    public MesaController(MesaService mesaService) {
        this.mesaService = mesaService;
    }

    // GET /mesas/disponiveis
    @GetMapping("/disponiveis")
    public List<Mesa> listarDisponiveis() {
        return mesaService.listarDisponiveis();
    }

     //POST /mesas/{id}/reservar
    @PostMapping("/{id}/reservar")
    public void reservar(@PathVariable Long id) {
         mesaService.reservar(id);
    }

    // POST /mesas/{id}/liberar
    @PostMapping("/{id}/liberar")
    public Mesa liberar(@PathVariable Long id) {
        return mesaService.liberar(id);
    }
}
