package com.seupedido.Controller;

import com.seupedido.Model.ItemPedido;
import com.seupedido.Service.CozinhaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/cozinha/view")
public class CozinhaViewController {

    private final CozinhaService cozinhaService;

    public CozinhaViewController(CozinhaService cozinhaService) {
        this.cozinhaService = cozinhaService;
    }

    /**
     * Exibe a lista de itens pendentes de preparo
     * GET /cozinha/view
     */
    @GetMapping
    public String viewCozinha(Model model) {
        List<ItemPedido> pendentes = cozinhaService.listarPendentes();
        model.addAttribute("pendentes", pendentes);
        return "cozinha";
    }

    /**
     * Marca um item como pronto
     * POST /cozinha/view/pronto/{id}
     */
    @PostMapping("/pronto/{id}")
    public String marcarPronto(@PathVariable Long id) {
        cozinhaService.marcarComoPronto(id);
        return "redirect:/cozinha/view";
    }
}
