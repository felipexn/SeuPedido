// ItemPedidoDaoImpl.java
package com.seupedido.DAO;

import com.seupedido.Controller.ItemController;
import com.seupedido.Model.Item;
import com.seupedido.Model.ItemPedido;
import com.seupedido.Service.ItemService;

import com.seupedido.enums.StatusItemEnuns;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class ItemPedidoDAOImpl implements ItemPedidoDAO {
    private final JdbcTemplate jdbc;

    private final RowMapper<ItemPedido> rowMapper = new RowMapper<ItemPedido>() {

        @Override
        public ItemPedido mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new ItemPedido(
                    rs.getLong("id"),
                    rs.getLong("pedido_id"),
                    rs.getLong("item_id"),
                    rs.getInt("quantidade"),
                    StatusItemEnuns.valueOf(rs.getString("status"))
            );
        }
    };

    private final ItemDAO itemDao;
    public ItemPedidoDAOImpl(JdbcTemplate jdbc, ItemDAO itemDao) {
        this.jdbc = jdbc;
        this.itemDao = itemDao;
    }

    @Override
    public ItemPedido findById(Long id) {
        return null;
    }

    @Override
    public List<ItemPedido> findByPedidoId(Long pedidoId) {
        String sql = "SELECT * FROM item_pedido WHERE pedido_id = ?";
        return jdbc.query(sql, (rs, rowNum) -> {
            ItemPedido ip = new ItemPedido();
            ip.setId(rs.getLong("id"));
            ip.setPedidoId(rs.getLong("pedido_id"));
            ip.setItemId(rs.getLong("item_id"));
            ip.setQuantidade(rs.getInt("quantidade"));
            ip.setStatus(StatusItemEnuns.valueOf(rs.getString("status")));

            // Carrega o item completo
            Item item = itemDao.findById(rs.getLong("item_id"));
            ip.setItem(item);

            return ip;
        }, pedidoId);
    }



    @Override
    public List<ItemPedido> findPendingKitchenJobs() {
        return jdbc.query(
                "SELECT ip.* FROM item_pedido ip " +
                        "JOIN item i ON ip.item_id = i.id " +
                        "WHERE i.precisa_cozinha = TRUE AND ip.status = 'PENDENTE'",
                rowMapper
        );
    }

    @Override
    public List<ItemPedido> findReadyForAttendant() {
        return jdbc.query(
                "SELECT ip.* FROM item_pedido ip " +
                        "JOIN item i ON ip.item_id = i.id " +
                        "WHERE (i.precisa_cozinha = TRUE AND ip.status = 'PRONTO') " +
                        "   OR (i.precisa_cozinha = FALSE AND ip.status = 'PENDENTE')",
                rowMapper
        );
    }

    @Override
    public Long create(ItemPedido ip) {
        KeyHolder kh = new GeneratedKeyHolder();
        jdbc.update(conn -> {
            PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO item_pedido (pedido_id, item_id, quantidade, status) VALUES (?, ?, ?, ?)",
                    new String[]{"id"}
            );
            ps.setLong(1, ip.getPedidoId());
            ps.setLong(2, ip.getItemId());
            ps.setInt(3, ip.getQuantidade());
            ps.setString(4, ip.getStatus().name());
            return ps;
        }, kh);
        return kh.getKey().longValue();
    }

    @Override
    public void updateStatus(Long idItemPedido, String status) {
        jdbc.update(
                "UPDATE item_pedido SET status = ? WHERE id = ?",
                status, idItemPedido
        );
    }

    @Override
    public void updateQuantity(Long idItemPedido, int quantidade) {
        jdbc.update(
                "UPDATE item_pedido SET quantidade = ? WHERE id = ?",
                quantidade, idItemPedido
        );
    }
}
