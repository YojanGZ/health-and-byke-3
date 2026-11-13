package healthandbike.repository;

import healthandbike.config.DatabaseConnection;
import healthandbike.domain.Recordatorio;
import healthandbike.domain.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RecordatorioRepository {

    private final UserRepository userRepository;

    public RecordatorioRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Recordatorio saveRecordatorio(Recordatorio recordatorio) {
        String insertSql = "INSERT INTO recordatorios (title, description, reminder_date, status, user_id) VALUES (?, ?, ?, ?, ?)";
        String updateSql = "UPDATE recordatorios SET title=?, description=?, reminder_date=?, status=?, user_id=? WHERE id_recordatorio=?";

        try (Connection conn = DatabaseConnection.getConnection()) {
            Integer userId = (recordatorio.getUser() != null && recordatorio.getUser().getId() > 0)
                    ? recordatorio.getUser().getId() : null;

            if (recordatorio.getIdRecordatorio() > 0 && findRecordatorioById(recordatorio.getIdRecordatorio()).isPresent()) {
                try (PreparedStatement ps = conn.prepareStatement(updateSql)) {
                    ps.setString(1, recordatorio.getTitle());
                    ps.setString(2, recordatorio.getDescription());
                    ps.setString(3, recordatorio.getReminderDate());
                    ps.setBoolean(4, recordatorio.isStatus());
                    if (userId != null) ps.setInt(5, userId); else ps.setNull(5, Types.INTEGER);
                    ps.setInt(6, recordatorio.getIdRecordatorio());
                    ps.executeUpdate();
                }
            } else {
                try (PreparedStatement ps = conn.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS)) {
                    ps.setString(1, recordatorio.getTitle());
                    ps.setString(2, recordatorio.getDescription());
                    ps.setString(3, recordatorio.getReminderDate());
                    ps.setBoolean(4, recordatorio.isStatus());
                    if (userId != null) ps.setInt(5, userId); else ps.setNull(5, Types.INTEGER);
                    ps.executeUpdate();

                    try (ResultSet rs = ps.getGeneratedKeys()) {
                        if (rs.next()) {
                            recordatorio.setIdRecordatorio(rs.getInt(1));
                        }
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al guardar recordatorio en MySQL: " + e.getMessage());
        }
        return recordatorio;
    }

    public List<Recordatorio> findAllRecordatorios() {
        List<Recordatorio> list = new ArrayList<>();
        String sql = "SELECT * FROM recordatorios ORDER BY id_recordatorio ASC";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(mapResultSetToRecordatorio(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error al listar recordatorios desde MySQL: " + e.getMessage());
        }
        return list;
    }

    public Optional<Recordatorio> findRecordatorioById(int id) {
        String sql = "SELECT * FROM recordatorios WHERE id_recordatorio = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapResultSetToRecordatorio(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar recordatorio por ID en MySQL: " + e.getMessage());
        }
        return Optional.empty();
    }

    public List<Recordatorio> findRecordatoriosByUserId(int userId) {
        List<Recordatorio> list = new ArrayList<>();
        String sql = "SELECT * FROM recordatorios WHERE user_id = ? ORDER BY id_recordatorio ASC";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapResultSetToRecordatorio(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar recordatorios por usuario en MySQL: " + e.getMessage());
        }
        return list;
    }

    public boolean deleteRecordatorio(int id) {
        String sql = "DELETE FROM recordatorios WHERE id_recordatorio = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al eliminar recordatorio en MySQL: " + e.getMessage());
            return false;
        }
    }

    private Recordatorio mapResultSetToRecordatorio(ResultSet rs) throws SQLException {
        int idRec = rs.getInt("id_recordatorio");
        String title = rs.getString("title");
        String desc = rs.getString("description");
        String date = rs.getString("reminder_date");
        boolean status = rs.getBoolean("status");
        int userId = rs.getInt("user_id");

        User user = null;
        if (!rs.wasNull() && userId > 0 && userRepository != null) {
            user = userRepository.findUserById(userId).orElse(null);
        }

        return new Recordatorio(idRec, title, desc, date, status, user);
    }
}
