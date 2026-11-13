package healthandbike.repository;

import healthandbike.config.DatabaseConnection;
import healthandbike.domain.EstadoMoto;
import healthandbike.domain.TipoMoto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class EstadoMotoRepository {

    private final TipoMotoRepository tipoMotoRepository;

    public EstadoMotoRepository(TipoMotoRepository tipoMotoRepository) {
        this.tipoMotoRepository = tipoMotoRepository;
    }

    public EstadoMoto saveEstado(EstadoMoto estado) {
        String insertSql = "INSERT INTO estados_moto (mileage, date_register, status_description, moto_id) VALUES (?, ?, ?, ?)";
        String updateSql = "UPDATE estados_moto SET mileage=?, date_register=?, status_description=?, moto_id=? WHERE id_estado=?";

        try (Connection conn = DatabaseConnection.getConnection()) {
            Integer motoId = (estado.getTipoMoto() != null && estado.getTipoMoto().getIdMoto() > 0)
                    ? estado.getTipoMoto().getIdMoto() : null;

            if (estado.getIdEstado() > 0 && findEstadoById(estado.getIdEstado()).isPresent()) {
                try (PreparedStatement ps = conn.prepareStatement(updateSql)) {
                    ps.setDouble(1, estado.getMileage());
                    ps.setString(2, estado.getDateRegister());
                    ps.setString(3, estado.getStatusDescription());
                    if (motoId != null) ps.setInt(4, motoId); else ps.setNull(4, Types.INTEGER);
                    ps.setInt(5, estado.getIdEstado());
                    ps.executeUpdate();
                }
            } else {
                try (PreparedStatement ps = conn.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS)) {
                    ps.setDouble(1, estado.getMileage());
                    ps.setString(2, estado.getDateRegister());
                    ps.setString(3, estado.getStatusDescription());
                    if (motoId != null) ps.setInt(4, motoId); else ps.setNull(4, Types.INTEGER);
                    ps.executeUpdate();

                    try (ResultSet rs = ps.getGeneratedKeys()) {
                        if (rs.next()) {
                            estado.setIdEstado(rs.getInt(1));
                        }
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al guardar estado de moto en MySQL: " + e.getMessage());
        }
        return estado;
    }

    public List<EstadoMoto> findAllEstados() {
        List<EstadoMoto> list = new ArrayList<>();
        String sql = "SELECT * FROM estados_moto ORDER BY id_estado ASC";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(mapResultSetToEstado(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error al listar estados de moto en MySQL: " + e.getMessage());
        }
        return list;
    }

    public Optional<EstadoMoto> findEstadoById(int id) {
        String sql = "SELECT * FROM estados_moto WHERE id_estado = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapResultSetToEstado(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar estado por ID en MySQL: " + e.getMessage());
        }
        return Optional.empty();
    }

    public List<EstadoMoto> findEstadosByMotoId(int motoId) {
        List<EstadoMoto> list = new ArrayList<>();
        String sql = "SELECT * FROM estados_moto WHERE moto_id = ? ORDER BY id_estado ASC";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, motoId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapResultSetToEstado(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar estados por moto en MySQL: " + e.getMessage());
        }
        return list;
    }

    public boolean deleteEstado(int id) {
        String sql = "DELETE FROM estados_moto WHERE id_estado = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al eliminar estado en MySQL: " + e.getMessage());
            return false;
        }
    }

    private EstadoMoto mapResultSetToEstado(ResultSet rs) throws SQLException {
        int idEstado = rs.getInt("id_estado");
        double mileage = rs.getDouble("mileage");
        String dateRegister = rs.getString("date_register");
        String statusDescription = rs.getString("status_description");
        int motoId = rs.getInt("moto_id");

        TipoMoto moto = null;
        if (!rs.wasNull() && motoId > 0 && tipoMotoRepository != null) {
            moto = tipoMotoRepository.findMotoById(motoId).orElse(null);
        }

        return new EstadoMoto(idEstado, mileage, dateRegister, statusDescription, moto);
    }
}
