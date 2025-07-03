// MesaDaoImpl.java
package com.seupedido.DAO;

import com.seupedido.Model.Mesa;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MesaDAOImpl implements MesaDAO {
    private final JdbcTemplate jdbc;
    private final RowMapper<Mesa> rowMapper = (rs, rn) ->
            new Mesa(rs.getLong("id"), rs.getBoolean("ocupada"));

    public MesaDAOImpl(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public List<Mesa> findAll() {
        return jdbc.query("SELECT id, ocupada FROM mesa", rowMapper);
    }

    @Override
    public List<Mesa> findAvailable() {
        return jdbc.query(
                "SELECT id, ocupada FROM mesa WHERE ocupada = FALSE",
                rowMapper
        );
    }

    @Override
    public Mesa findById(Long id) {
        return jdbc.queryForObject(
                "SELECT id, ocupada FROM mesa WHERE id = ?",
                rowMapper, id
        );
    }

    @Override
    public void update(Mesa mesa) {
        jdbc.update(
                "UPDATE mesa SET ocupada = ? WHERE id = ?",
                mesa.isOcupada(), mesa.getId()
        );
    }
}
