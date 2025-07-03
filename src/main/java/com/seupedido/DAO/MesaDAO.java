// MesaDao.java
package com.seupedido.DAO;

import com.seupedido.Model.Mesa;
import java.util.List;

public interface MesaDAO {
    List<Mesa> findAll();
    List<Mesa> findAvailable();
    Mesa findById(Long id);
    void update(Mesa mesa);
}
