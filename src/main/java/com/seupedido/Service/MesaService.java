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

    public Mesa reservar(Long idMesa) {
        Mesa mesa = mesaDao.findById(idMesa);
        if (mesa.isOcupada()) {
            throw new IllegalStateException("Mesa já está ocupada");
        }
        mesa.setOcupada(true);
        mesaDao.update(mesa);
        return mesa;
    }

    public Mesa liberar(Long idMesa) {
        Mesa mesa = mesaDao.findById(idMesa);
        mesa.setOcupada(false);
        mesaDao.update(mesa);
        return mesa;
    }
}
