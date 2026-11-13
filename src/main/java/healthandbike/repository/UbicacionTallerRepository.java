package healthandbike.repository;

import healthandbike.config.DatabaseConnection;
import healthandbike.domain.UbicacionTaller;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UbicacionTallerRepository {

    public UbicacionTaller saveTaller(UbicacionTaller taller) {
        String insertSql = "INSERT INTO ubicaciones_taller (workshop_name, address, city, phone, latitude, longitude) VALUES (?, ?, ?, ?, ?, ?)";
        String updateSql = "UPDATE ubicaciones_taller SET workshop_name=?, address=?, city=?, phone=?, latitude=?, longitude=? WHERE id_taller=?";

        try (Connection conn = DatabaseConnection.getConnection()) {
            if (taller.getIdTaller() > 0 && findTallerById(taller.getIdTaller()).isPresent()) {
                try (PreparedStatement ps = conn.prepareStatement(updateSql)) {
                    ps.setString(1, taller.getWorkshopName());
                    ps.setString(2, taller.getAddress());
                    ps.setString(3, taller.getCity());
                    ps.setString(4, taller.getPhone());
                    ps.setDouble(5, taller.getLatitude());
                    ps.setDouble(6, taller.getLongitude());
                    ps.setInt(7, taller.getIdTaller());
                    ps.executeUpdate();
                }
            } else {
                try (PreparedStatement ps = conn.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS)) {
                    ps.setString(1, taller.getWorkshopName());
                    ps.setString(2, taller.getAddress());
                    ps.setString(3, taller.getCity());
                    ps.setString(4, taller.getPhone());
                    ps.setDouble(5, taller.getLatitude());
                    ps.setDouble(6, taller.getLongitude());
                    ps.executeUpdate();

                    try (ResultSet rs = ps.getGeneratedKeys()) {
                        if (rs.next()) {
                            taller.setIdTaller(rs.getInt(1));
                        }
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al guardar taller en MySQL: " + e.getMessage());
        }
        return taller;
    }

    public List<UbicacionTaller> findAllTalleres() {
        List<UbicacionTaller> list = new ArrayList<>();
        String sql = "SELECT * FROM ubicaciones_taller ORDER BY id_taller ASC";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(mapResultSetToTaller(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error al listar talleres desde MySQL: " + e.getMessage());
        }
        return list;
    }

    public Optional<UbicacionTaller> findTallerById(int id) {
        String sql = "SELECT * FROM ubicaciones_taller WHERE id_taller = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapResultSetToTaller(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar taller por ID en MySQL: " + e.getMessage());
        }
        return Optional.empty();
    }

    public List<UbicacionTaller> findTalleresByCity(String city) {
        List<UbicacionTaller> list = new ArrayList<>();
        String sql = "SELECT * FROM ubicaciones_taller WHERE LOWER(city) = LOWER(?) ORDER BY id_taller ASC";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, city);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapResultSetToTaller(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar talleres por ciudad en MySQL: " + e.getMessage());
        }
        return list;
    }

    public boolean deleteTaller(int id) {
        String sql = "DELETE FROM ubicaciones_taller WHERE id_taller = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al eliminar taller en MySQL: " + e.getMessage());
            return false;
        }
    }

    private UbicacionTaller mapResultSetToTaller(ResultSet rs) throws SQLException {
        return new UbicacionTaller(
                rs.getInt("id_taller"),
                rs.getString("workshop_name"),
                rs.getString("address"),
                rs.getString("city"),
                rs.getString("phone"),
                rs.getDouble("latitude"),
                rs.getDouble("longitude")
        );
    }
}
