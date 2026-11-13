package healthandbike.repository;

import healthandbike.config.DatabaseConnection;
import healthandbike.domain.Admin;
import healthandbike.domain.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserRepository {

    // --- OPERACIONES DE USUARIOS (CLIENTES) ---

    public User saveUser(User user) {
        String insertSql = "INSERT INTO users (name, last_name, email, password, status, phone, role, address, balance) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        String updateSql = "UPDATE users SET name=?, last_name=?, email=?, password=?, status=?, phone=?, role=?, address=?, balance=? WHERE id=?";

        try (Connection conn = DatabaseConnection.getConnection()) {
            if (user.getId() > 0 && findUserById(user.getId()).isPresent()) {
                try (PreparedStatement ps = conn.prepareStatement(updateSql)) {
                    ps.setString(1, user.getName());
                    ps.setString(2, user.getLastName());
                    ps.setString(3, user.getEmail());
                    ps.setString(4, user.getPassword());
                    ps.setBoolean(5, user.isStatus());
                    ps.setString(6, user.getPhone());
                    ps.setString(7, user.getRole() != null ? user.getRole() : "CLIENTE");
                    ps.setString(8, user.getAddress());
                    ps.setDouble(9, user.getBalance());
                    ps.setInt(10, user.getId());
                    ps.executeUpdate();
                }
            } else {
                try (PreparedStatement ps = conn.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS)) {
                    ps.setString(1, user.getName());
                    ps.setString(2, user.getLastName());
                    ps.setString(3, user.getEmail());
                    ps.setString(4, user.getPassword());
                    ps.setBoolean(5, user.isStatus());
                    ps.setString(6, user.getPhone());
                    ps.setString(7, user.getRole() != null ? user.getRole() : "CLIENTE");
                    ps.setString(8, user.getAddress());
                    ps.setDouble(9, user.getBalance());
                    ps.executeUpdate();

                    try (ResultSet rs = ps.getGeneratedKeys()) {
                        if (rs.next()) {
                            user.setId(rs.getInt(1));
                        }
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al guardar usuario en MySQL: " + e.getMessage());
        }
        return user;
    }

    public List<User> findAllUsers() {
        List<User> list = new ArrayList<>();
        String sql = "SELECT * FROM users ORDER BY id ASC";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(mapResultSetToUser(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error al listar usuarios desde MySQL: " + e.getMessage());
        }
        return list;
    }

    public Optional<User> findUserById(int id) {
        String sql = "SELECT * FROM users WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapResultSetToUser(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar usuario por ID en MySQL: " + e.getMessage());
        }
        return Optional.empty();
    }

    public Optional<User> findUserByEmail(String email) {
        String sql = "SELECT * FROM users WHERE LOWER(email) = LOWER(?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapResultSetToUser(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar usuario por Email en MySQL: " + e.getMessage());
        }
        return Optional.empty();
    }

    public boolean deleteUser(int id) {
        String sql = "DELETE FROM users WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al eliminar usuario en MySQL: " + e.getMessage());
            return false;
        }
    }

    // --- OPERACIONES DE ADMINISTRADORES ---

    public Admin saveAdmin(Admin admin) {
        String insertSql = "INSERT INTO admins (name, last_name, email, password, status, phone, department, access_level) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        String updateSql = "UPDATE admins SET name=?, last_name=?, email=?, password=?, status=?, phone=?, department=?, access_level=? WHERE id=?";

        try (Connection conn = DatabaseConnection.getConnection()) {
            if (admin.getId() > 0 && findAdminById(admin.getId()).isPresent()) {
                try (PreparedStatement ps = conn.prepareStatement(updateSql)) {
                    ps.setString(1, admin.getName());
                    ps.setString(2, admin.getLastName());
                    ps.setString(3, admin.getEmail());
                    ps.setString(4, admin.getPassword());
                    ps.setBoolean(5, admin.isStatus());
                    ps.setString(6, admin.getPhone());
                    ps.setString(7, admin.getDepartment());
                    ps.setString(8, admin.getAccessLevel());
                    ps.setInt(9, admin.getId());
                    ps.executeUpdate();
                }
            } else {
                try (PreparedStatement ps = conn.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS)) {
                    ps.setString(1, admin.getName());
                    ps.setString(2, admin.getLastName());
                    ps.setString(3, admin.getEmail());
                    ps.setString(4, admin.getPassword());
                    ps.setBoolean(5, admin.isStatus());
                    ps.setString(6, admin.getPhone());
                    ps.setString(7, admin.getDepartment());
                    ps.setString(8, admin.getAccessLevel());
                    ps.executeUpdate();

                    try (ResultSet rs = ps.getGeneratedKeys()) {
                        if (rs.next()) {
                            admin.setId(rs.getInt(1));
                        }
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al guardar admin en MySQL: " + e.getMessage());
        }
        return admin;
    }

    public List<Admin> findAllAdmins() {
        List<Admin> list = new ArrayList<>();
        String sql = "SELECT * FROM admins ORDER BY id ASC";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(mapResultSetToAdmin(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error al listar administradores desde MySQL: " + e.getMessage());
        }
        return list;
    }

    public Optional<Admin> findAdminById(int id) {
        String sql = "SELECT * FROM admins WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapResultSetToAdmin(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar admin por ID en MySQL: " + e.getMessage());
        }
        return Optional.empty();
    }

    public Optional<Admin> findAdminByEmail(String email) {
        String sql = "SELECT * FROM admins WHERE LOWER(email) = LOWER(?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapResultSetToAdmin(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar admin por Email en MySQL: " + e.getMessage());
        }
        return Optional.empty();
    }

    // --- MÉTODOS AUXILIARES DE MAPEO ---

    private User mapResultSetToUser(ResultSet rs) throws SQLException {
        return new User(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getString("last_name"),
                rs.getString("email"),
                rs.getString("password"),
                rs.getBoolean("status"),
                rs.getString("phone"),
                rs.getString("role"),
                rs.getString("address"),
                rs.getDouble("balance")
        );
    }

    private Admin mapResultSetToAdmin(ResultSet rs) throws SQLException {
        return new Admin(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getString("last_name"),
                rs.getString("email"),
                rs.getString("password"),
                rs.getBoolean("status"),
                rs.getString("phone"),
                rs.getString("department"),
                rs.getString("access_level")
        );
    }
}
