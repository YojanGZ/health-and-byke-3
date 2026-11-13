package healthandbike.repository;

import healthandbike.config.DatabaseConnection;
import healthandbike.domain.TipoMoto;
import healthandbike.domain.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TipoMotoRepository {

    private final UserRepository userRepository;

    public TipoMotoRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public TipoMoto saveMoto(TipoMoto moto) {
        String insertSql = "INSERT INTO tipos_moto (brand, model, cylinder_capacity, license_plate, year, user_id) VALUES (?, ?, ?, ?, ?, ?)";
        String updateSql = "UPDATE tipos_moto SET brand=?, model=?, cylinder_capacity=?, license_plate=?, year=?, user_id=? WHERE id_moto=?";

        try (Connection conn = DatabaseConnection.getConnection()) {
            Integer userId = (moto.getUser() != null && moto.getUser().getId() > 0) ? moto.getUser().getId() : null;

            if (moto.getIdMoto() > 0 && findMotoById(moto.getIdMoto()).isPresent()) {
                try (PreparedStatement ps = conn.prepareStatement(updateSql)) {
                    ps.setString(1, moto.getBrand());
                    ps.setString(2, moto.getModel());
                    ps.setString(3, moto.getCylinderCapacity());
                    ps.setString(4, moto.getLicensePlate());
                    ps.setInt(5, moto.getYear());
                    if (userId != null) ps.setInt(6, userId); else ps.setNull(6, Types.INTEGER);
                    ps.setInt(7, moto.getIdMoto());
                    ps.executeUpdate();
                }
            } else {
                try (PreparedStatement ps = conn.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS)) {
                    ps.setString(1, moto.getBrand());
                    ps.setString(2, moto.getModel());
                    ps.setString(3, moto.getCylinderCapacity());
                    ps.setString(4, moto.getLicensePlate());
                    ps.setInt(5, moto.getYear());
                    if (userId != null) ps.setInt(6, userId); else ps.setNull(6, Types.INTEGER);
                    ps.executeUpdate();

                    try (ResultSet rs = ps.getGeneratedKeys()) {
                        if (rs.next()) {
                            moto.setIdMoto(rs.getInt(1));
                        }
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al guardar moto en MySQL: " + e.getMessage());
        }
        return moto;
    }

    public List<TipoMoto> findAllMotos() {
        List<TipoMoto> list = new ArrayList<>();
        String sql = "SELECT * FROM tipos_moto ORDER BY id_moto ASC";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(mapResultSetToMoto(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error al listar motos desde MySQL: " + e.getMessage());
        }
        return list;
    }

    public Optional<TipoMoto> findMotoById(int id) {
        String sql = "SELECT * FROM tipos_moto WHERE id_moto = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapResultSetToMoto(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar moto por ID en MySQL: " + e.getMessage());
        }
        return Optional.empty();
    }

    public Optional<TipoMoto> findMotoByPlate(String plate) {
        String sql = "SELECT * FROM tipos_moto WHERE UPPER(license_plate) = UPPER(?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, plate);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapResultSetToMoto(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar moto por placa en MySQL: " + e.getMessage());
        }
        return Optional.empty();
    }

    public List<TipoMoto> findMotosByUserId(int userId) {
        List<TipoMoto> list = new ArrayList<>();
        String sql = "SELECT * FROM tipos_moto WHERE user_id = ? ORDER BY id_moto ASC";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapResultSetToMoto(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar motos por usuario en MySQL: " + e.getMessage());
        }
        return list;
    }

    public boolean deleteMoto(int id) {
        String sql = "DELETE FROM tipos_moto WHERE id_moto = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al eliminar moto en MySQL: " + e.getMessage());
            return false;
        }
    }

    private TipoMoto mapResultSetToMoto(ResultSet rs) throws SQLException {
        int idMoto = rs.getInt("id_moto");
        String brand = rs.getString("brand");
        String model = rs.getString("model");
        String cc = rs.getString("cylinder_capacity");
        String plate = rs.getString("license_plate");
        int year = rs.getInt("year");
        int userId = rs.getInt("user_id");

        User user = null;
        if (!rs.wasNull() && userId > 0 && userRepository != null) {
            user = userRepository.findUserById(userId).orElse(null);
        }

        return new TipoMoto(idMoto, brand, model, cc, plate, year, user);
    }
}
