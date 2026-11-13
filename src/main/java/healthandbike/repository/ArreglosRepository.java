package healthandbike.repository;

import healthandbike.config.DatabaseConnection;
import healthandbike.domain.Arreglos;
import healthandbike.domain.Historial;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ArreglosRepository {

    private final HistorialRepository historialRepository;

    public ArreglosRepository(HistorialRepository historialRepository) {
        this.historialRepository = historialRepository;
    }

    public Arreglos saveArreglo(Arreglos arreglo) {
        String insertSql = "INSERT INTO arreglos (description, labor_cost, historial_id) VALUES (?, ?, ?)";
        String updateSql = "UPDATE arreglos SET description=?, labor_cost=?, historial_id=? WHERE id_arreglo=?";

        try (Connection conn = DatabaseConnection.getConnection()) {
            Integer historialId = (arreglo.getHistorial() != null && arreglo.getHistorial().getIdHistorial() > 0)
                    ? arreglo.getHistorial().getIdHistorial() : null;

            if (arreglo.getIdArreglo() > 0 && findArregloById(arreglo.getIdArreglo()).isPresent()) {
                try (PreparedStatement ps = conn.prepareStatement(updateSql)) {
                    ps.setString(1, arreglo.getDescription());
                    ps.setDouble(2, arreglo.getLaborCost());
                    if (historialId != null) ps.setInt(3, historialId); else ps.setNull(3, Types.INTEGER);
                    ps.setInt(4, arreglo.getIdArreglo());
                    ps.executeUpdate();
                }
            } else {
                try (PreparedStatement ps = conn.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS)) {
                    ps.setString(1, arreglo.getDescription());
                    ps.setDouble(2, arreglo.getLaborCost());
                    if (historialId != null) ps.setInt(3, historialId); else ps.setNull(3, Types.INTEGER);
                    ps.executeUpdate();

                    try (ResultSet rs = ps.getGeneratedKeys()) {
                        if (rs.next()) {
                            arreglo.setIdArreglo(rs.getInt(1));
                        }
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al guardar arreglo en MySQL: " + e.getMessage());
        }
        return arreglo;
    }

    public List<Arreglos> findAllArreglos() {
        List<Arreglos> list = new ArrayList<>();
        String sql = "SELECT * FROM arreglos ORDER BY id_arreglo ASC";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(mapResultSetToArreglo(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error al listar arreglos desde MySQL: " + e.getMessage());
        }
        return list;
    }

    public Optional<Arreglos> findArregloById(int id) {
        String sql = "SELECT * FROM arreglos WHERE id_arreglo = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapResultSetToArreglo(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar arreglo por ID en MySQL: " + e.getMessage());
        }
        return Optional.empty();
    }

    public List<Arreglos> findArreglosByHistorialId(int historialId) {
        List<Arreglos> list = new ArrayList<>();
        String sql = "SELECT * FROM arreglos WHERE historial_id = ? ORDER BY id_arreglo ASC";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, historialId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapResultSetToArreglo(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar arreglos por historial en MySQL: " + e.getMessage());
        }
        return list;
    }

    public boolean deleteArreglo(int id) {
        String sql = "DELETE FROM arreglos WHERE id_arreglo = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al eliminar arreglo en MySQL: " + e.getMessage());
            return false;
        }
    }

    private Arreglos mapResultSetToArreglo(ResultSet rs) throws SQLException {
        int idArreglo = rs.getInt("id_arreglo");
        String desc = rs.getString("description");
        double labor = rs.getDouble("labor_cost");
        int histId = rs.getInt("historial_id");

        Historial historial = null;
        if (!rs.wasNull() && histId > 0 && historialRepository != null) {
            historial = historialRepository.findHistorialById(histId).orElse(null);
        }

        return new Arreglos(idArreglo, desc, labor, historial);
    }
}
