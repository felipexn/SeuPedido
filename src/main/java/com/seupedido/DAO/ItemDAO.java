// ItemDao.java
package com.seupedido.DAO;

import com.seupedido.Model.Item;
import java.util.List;

public interface ItemDAO {
    List<Item> findAll();
    Item findById(Long id);
    Long create(Item item);
    void update(Item item);
    void delete(Long id);
}
