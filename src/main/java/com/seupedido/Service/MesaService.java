package com.seupedido.Service;

import com.seupedido.DAO.MesaDAO;
import com.seupedido.Model.Mesa;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MesaService {

    private final MesaDAO mesaDao;

    public MesaService(MesaDAO mesaDao) {
        this.mesaDao = mesaDao;
    }

    public List<Mesa> listarDisponiveis() {
        return mesaDao.findAvailable();
    }

    public void reservar(Long idMesa) {
        Mesa mesa = mesaDao.findById(idMesa);
        if (mesa.isOcupada()) {
            return;
        }
        mesa.setOcupada(true);
        mesaDao.update(mesa);

    }
    public Mesa buscarPorId(Long id) {
        return mesaDao.findById(id);
    }

    public Mesa liberar(Long idMesa) {
        Mesa mesa = mesaDao.findById(idMesa);
        mesa.setOcupada(false);
        mesaDao.update(mesa);
        return mesa;
    }
}
