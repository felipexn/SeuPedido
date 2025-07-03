// ItemDaoImpl.java
package com.seupedido.DAO;

import com.seupedido.Model.Item;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.util.List;

@Repository
public class ItemDAOImpl implements ItemDAO {
    private final JdbcTemplate jdbc;
    private final RowMapper<Item> rowMapper = (rs, rn) ->
            new Item(
                    rs.getLong("id"),
                    rs.getString("produto"),
                    rs.getBigDecimal("preco"),
                    rs.getBoolean("precisa_cozinha")
            );

    public ItemDAOImpl(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public List<Item> findAll() {
        return jdbc.query(
                "SELECT id, produto, preco, precisa_cozinha FROM item",
                rowMapper
        );
    }

    @Override
    public Item findById(Long id) {
        return jdbc.queryForObject(
                "SELECT id, produto, preco, precisa_cozinha FROM item WHERE id = ?",
                rowMapper, id
        );
    }

    @Override
    public Long create(Item item) {
        KeyHolder kh = new GeneratedKeyHolder();
        jdbc.update(conn -> {
            PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO item (produto, preco, precisa_cozinha) VALUES (?, ?, ?)",
                    new String[]{"id"}
            );
            ps.setString(1, item.getProduto());
            ps.setBigDecimal(2, item.getPreco());
            ps.setBoolean(3, item.isPrecisaCozinha());
            return ps;
        }, kh);
        return kh.getKey().longValue();
    }

    @Override
    public void update(Item item) {
        jdbc.update(
                "UPDATE item SET produto = ?, preco = ?, precisa_cozinha = ? WHERE id = ?",
                item.getProduto(), item.getPreco(), item.isPrecisaCozinha(), item.getId()
        );
    }

    @Override
    public void delete(Long id) {
        jdbc.update("DELETE FROM item WHERE id = ?", id);
    }
}
