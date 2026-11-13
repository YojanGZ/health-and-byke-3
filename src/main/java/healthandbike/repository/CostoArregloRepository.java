package healthandbike.repository;

import healthandbike.config.DatabaseConnection;
import healthandbike.domain.Arreglos;
import healthandbike.domain.CostoArreglo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CostoArregloRepository {

    private final ArreglosRepository arreglosRepository;

    public CostoArregloRepository(ArreglosRepository arreglosRepository) {
        this.arreglosRepository = arreglosRepository;
    }

    public CostoArreglo saveCosto(CostoArreglo costo) {
        String insertSql = "INSERT INTO costos_arreglo (spare_parts_cost, labor_cost, total_amount, arreglo_id) VALUES (?, ?, ?, ?)";
        String updateSql = "UPDATE costos_arreglo SET spare_parts_cost=?, labor_cost=?, total_amount=?, arreglo_id=? WHERE id_costo=?";

        try (Connection conn = DatabaseConnection.getConnection()) {
            Integer arregloId = (costo.getArreglos() != null && costo.getArreglos().getIdArreglo() > 0)
                    ? costo.getArreglos().getIdArreglo() : null;

            if (costo.getIdCosto() > 0 && findCostoById(costo.getIdCosto()).isPresent()) {
                try (PreparedStatement ps = conn.prepareStatement(updateSql)) {
                    ps.setDouble(1, costo.getSparePartsCost());
                    ps.setDouble(2, costo.getLaborCost());
                    ps.setDouble(3, costo.getTotalAmount());
                    if (arregloId != null) ps.setInt(4, arregloId); else ps.setNull(4, Types.INTEGER);
                    ps.setInt(5, costo.getIdCosto());
                    ps.executeUpdate();
                }
            } else {
                try (PreparedStatement ps = conn.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS)) {
                    ps.setDouble(1, costo.getSparePartsCost());
                    ps.setDouble(2, costo.getLaborCost());
                    ps.setDouble(3, costo.getTotalAmount());
                    if (arregloId != null) ps.setInt(4, arregloId); else ps.setNull(4, Types.INTEGER);
                    ps.executeUpdate();

                    try (ResultSet rs = ps.getGeneratedKeys()) {
                        if (rs.next()) {
                            costo.setIdCosto(rs.getInt(1));
                        }
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al guardar costo en MySQL: " + e.getMessage());
        }
        return costo;
    }

    public List<CostoArreglo> findAllCostos() {
        List<CostoArreglo> list = new ArrayList<>();
        String sql = "SELECT * FROM costos_arreglo ORDER BY id_costo ASC";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(mapResultSetToCosto(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error al listar costos desde MySQL: " + e.getMessage());
        }
        return list;
    }

    public Optional<CostoArreglo> findCostoById(int id) {
        String sql = "SELECT * FROM costos_arreglo WHERE id_costo = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapResultSetToCosto(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar costo por ID en MySQL: " + e.getMessage());
        }
        return Optional.empty();
    }

    public Optional<CostoArreglo> findCostoByArregloId(int arregloId) {
        String sql = "SELECT * FROM costos_arreglo WHERE arreglo_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, arregloId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapResultSetToCosto(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar costo por arreglo en MySQL: " + e.getMessage());
        }
        return Optional.empty();
    }

    public boolean deleteCosto(int id) {
        String sql = "DELETE FROM costos_arreglo WHERE id_costo = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al eliminar costo en MySQL: " + e.getMessage());
            return false;
        }
    }

    private CostoArreglo mapResultSetToCosto(ResultSet rs) throws SQLException {
        int idCosto = rs.getInt("id_costo");
        double spare = rs.getDouble("spare_parts_cost");
        double labor = rs.getDouble("labor_cost");
        double total = rs.getDouble("total_amount");
        int arregloId = rs.getInt("arreglo_id");

        Arreglos arreglo = null;
        if (!rs.wasNull() && arregloId > 0 && arreglosRepository != null) {
            arreglo = arreglosRepository.findArregloById(arregloId).orElse(null);
        }

        return new CostoArreglo(idCosto, spare, labor, total, arreglo);
    }
}
