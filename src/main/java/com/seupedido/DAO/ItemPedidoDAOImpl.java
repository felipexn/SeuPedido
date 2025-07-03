// ItemPedidoDaoImpl.java
package com.seupedido.DAO;

import com.seupedido.Model.ItemPedido;
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

    public ItemPedidoDAOImpl(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public ItemPedido findById(Long id) {
        String sql = "SELECT * FROM item_pedido WHERE id = ?";
        return jdbc.query(sql, new Object[]{id}, rs -> {
            if (rs.next()) {
                ItemPedido item = new ItemPedido();
                item.setId(rs.getLong("id"));
                item.setPedidoId(rs.getLong("pedido_id"));
                item.setItemId(rs.getLong("item_id"));
                item.setStatus(StatusItemEnuns.valueOf(rs.getString("status")));
                return item;
            }
            return null;
        });
    }


    @Override
    public List<ItemPedido> findByPedidoId(Long pedidoId) {
        return jdbc.query(
                "SELECT * FROM item_pedido WHERE pedido_id = ?",
                rowMapper, pedidoId
        );
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
