package healthandbike.repository;

import healthandbike.config.DatabaseConnection;
import healthandbike.domain.Historial;
import healthandbike.domain.UbicacionTaller;
import healthandbike.domain.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class HistorialRepository {

    private final UserRepository userRepository;
    private final UbicacionTallerRepository tallerRepository;

    public HistorialRepository(UserRepository userRepository, UbicacionTallerRepository tallerRepository) {
        this.userRepository = userRepository;
        this.tallerRepository = tallerRepository;
    }

    public Historial saveHistorial(Historial historial) {
        String insertSql = "INSERT INTO historiales (entry_date, departure_date, total_cost, diagnostic, user_id, taller_id) VALUES (?, ?, ?, ?, ?, ?)";
        String updateSql = "UPDATE historiales SET entry_date=?, departure_date=?, total_cost=?, diagnostic=?, user_id=?, taller_id=? WHERE id_historial=?";

        try (Connection conn = DatabaseConnection.getConnection()) {
            Integer userId = (historial.getUser() != null && historial.getUser().getId() > 0)
                    ? historial.getUser().getId() : null;
            Integer tallerId = (historial.getTaller() != null && historial.getTaller().getIdTaller() > 0)
                    ? historial.getTaller().getIdTaller() : null;

            if (historial.getIdHistorial() > 0 && findHistorialById(historial.getIdHistorial()).isPresent()) {
                try (PreparedStatement ps = conn.prepareStatement(updateSql)) {
                    ps.setString(1, historial.getEntryDate());
                    ps.setString(2, historial.getDepartureDate());
                    ps.setDouble(3, historial.getTotalCost());
                    ps.setString(4, historial.getDiagnostic());
                    if (userId != null) ps.setInt(5, userId); else ps.setNull(5, Types.INTEGER);
                    if (tallerId != null) ps.setInt(6, tallerId); else ps.setNull(6, Types.INTEGER);
                    ps.setInt(7, historial.getIdHistorial());
                    ps.executeUpdate();
                }
            } else {
                try (PreparedStatement ps = conn.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS)) {
                    ps.setString(1, historial.getEntryDate());
                    ps.setString(2, historial.getDepartureDate());
                    ps.setDouble(3, historial.getTotalCost());
                    ps.setString(4, historial.getDiagnostic());
                    if (userId != null) ps.setInt(5, userId); else ps.setNull(5, Types.INTEGER);
                    if (tallerId != null) ps.setInt(6, tallerId); else ps.setNull(6, Types.INTEGER);
                    ps.executeUpdate();

                    try (ResultSet rs = ps.getGeneratedKeys()) {
                        if (rs.next()) {
                            historial.setIdHistorial(rs.getInt(1));
                        }
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al guardar historial en MySQL: " + e.getMessage());
        }
        return historial;
    }

    public List<Historial> findAllHistoriales() {
        List<Historial> list = new ArrayList<>();
        String sql = "SELECT * FROM historiales ORDER BY id_historial ASC";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(mapResultSetToHistorial(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error al listar historiales desde MySQL: " + e.getMessage());
        }
        return list;
    }

    public Optional<Historial> findHistorialById(int id) {
        String sql = "SELECT * FROM historiales WHERE id_historial = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapResultSetToHistorial(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar historial por ID en MySQL: " + e.getMessage());
        }
        return Optional.empty();
    }

    public List<Historial> findHistorialesByUserId(int userId) {
        List<Historial> list = new ArrayList<>();
        String sql = "SELECT * FROM historiales WHERE user_id = ? ORDER BY id_historial ASC";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapResultSetToHistorial(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar historiales por usuario en MySQL: " + e.getMessage());
        }
        return list;
    }

    public boolean deleteHistorial(int id) {
        String sql = "DELETE FROM historiales WHERE id_historial = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al eliminar historial en MySQL: " + e.getMessage());
            return false;
        }
    }

    private Historial mapResultSetToHistorial(ResultSet rs) throws SQLException {
        int idHistorial = rs.getInt("id_historial");
        String entryDate = rs.getString("entry_date");
        String departureDate = rs.getString("departure_date");
        double totalCost = rs.getDouble("total_cost");
        String diagnostic = rs.getString("diagnostic");
        int userId = rs.getInt("user_id");
        int tallerId = rs.getInt("taller_id");

        User user = null;
        if (!rs.wasNull() && userId > 0 && userRepository != null) {
            user = userRepository.findUserById(userId).orElse(null);
        }

        UbicacionTaller taller = null;
        if (!rs.wasNull() && tallerId > 0 && tallerRepository != null) {
            taller = tallerRepository.findTallerById(tallerId).orElse(null);
        }

        return new Historial(idHistorial, entryDate, departureDate, totalCost, diagnostic, user, taller);
    }
}
