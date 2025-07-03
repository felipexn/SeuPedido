package com.seupedido.Service;

import com.seupedido.DAO.ItemDAO;
import com.seupedido.Model.Item;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemService {
    private final ItemDAO itemDao;

    public ItemService(ItemDAO itemDao) {
        this.itemDao = itemDao;
    }

    public List<Item> listarTodos() {
        return itemDao.findAll();
    }

    public Item buscarPorId(Long id) {
        return itemDao.findById(id);
    }

    public Long criarItem(Item item) {
        return itemDao.create(item);
    }

    public void atualizarItem(Long id, Item item) {
        item.setId(id);
        itemDao.update(item);
    }

    public void removerItem(Long id) {
        itemDao.delete(id);
    }
}
