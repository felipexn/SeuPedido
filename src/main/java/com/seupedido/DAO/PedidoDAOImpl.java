// PedidoDaoImpl.java
package com.seupedido.DAO;

import com.seupedido.Model.Pedido;
import com.seupedido.enums.StatusPedidoEnuns;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class PedidoDAOImpl implements PedidoDAO {
    private final JdbcTemplate jdbc;
    private final RowMapper<Pedido> rowMapper = new RowMapper<Pedido>() {
        @Override
        public Pedido mapRow(ResultSet rs, int rowNum) throws SQLException {
            Pedido p = new Pedido();
            p.setId(rs.getLong("id"));
            p.setData(rs.getTimestamp("data").toLocalDateTime());
            p.setTotal(rs.getBigDecimal("total"));
            p.setStatus(StatusPedidoEnuns.valueOf(rs.getString("status")));
            p.setMesaId(rs.getLong("mesa_id"));
            return p;
        }
    };

    public PedidoDAOImpl(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public Pedido findById(Long id) {
        return jdbc.queryForObject(
                "SELECT id, data, total, status, mesa_id FROM pedido WHERE id = ?",
                rowMapper, id
        );
    }
    @Override
    public void update(Pedido pedido) {
        String sql = "UPDATE pedido SET data = ?, total = ?, status = ?, mesa_id = ? WHERE id = ?";
        jdbc.update(conn -> {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setTimestamp(1, java.sql.Timestamp.valueOf(
                    pedido.getData() != null ? pedido.getData() : LocalDateTime.now()));
            ps.setBigDecimal(2, pedido.getTotal());
            ps.setString(3, pedido.getStatus().name());
            ps.setLong(4, pedido.getMesaId());
            ps.setLong(5, pedido.getId());
            return ps;
        });
    }

    @Override
    public Pedido findAbertoByMesa(Long idMesa) {
        String sql = "SELECT * FROM pedido WHERE mesa_id = ? AND status = 'ABERTO' LIMIT 1";
        return jdbc.query(sql, new Object[]{idMesa}, rs -> {
            if (rs.next()) {
                Pedido pedido = new Pedido();
                pedido.setId(rs.getLong("id"));
                pedido.setData(rs.getTimestamp("data").toLocalDateTime());
                pedido.setTotal(rs.getBigDecimal("total"));
                pedido.setStatus(StatusPedidoEnuns.valueOf(rs.getString("status")));
                pedido.setMesaId(rs.getLong("mesa_id"));
                return pedido;
            } else {
                return null;
            }
        });
    }


    @Override
    public List<Pedido> findByMesaId(Long mesaId) {
        return jdbc.query(
                "SELECT id, data, total, status, mesa_id FROM pedido WHERE mesa_id = ?",
                rowMapper, mesaId
        );
    }

    @Override
    public Long create(Pedido pedido) {
        KeyHolder kh = new GeneratedKeyHolder();
        jdbc.update(conn -> {
            PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO pedido (data, total, status, mesa_id) VALUES (?, ?, ?, ?)",
                    new String[]{"id"}
            );
            ps.setTimestamp(1, java.sql.Timestamp.valueOf(
                    pedido.getData() != null ? pedido.getData() : LocalDateTime.now()));
            ps.setBigDecimal(2, pedido.getTotal());
            ps.setString(3, pedido.getStatus().name());
            ps.setLong(4, pedido.getMesaId());
            return ps;
        }, kh);
        return kh.getKey().longValue();
    }

    @Override
    public void updateTotal(Long pedidoId, BigDecimal total) {
        jdbc.update(
                "UPDATE pedido SET total = ? WHERE id = ?",
                total, pedidoId
        );
    }

    @Override
    public void updateStatus(Long pedidoId, String status) {
        jdbc.update(
                "UPDATE pedido SET status = ? WHERE id = ?",
                status, pedidoId
        );
    }
}
