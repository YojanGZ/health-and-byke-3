package healthandbike.services;

import healthandbike.domain.Admin;

import java.util.List;
import java.util.Optional;

public interface AdminService {

    Admin createAdmin(Admin admin);
    Optional<Admin> getAdminById(int id);
    Optional<Admin> getAdminByEmail(String email);
    List<Admin> getAllAdmins();
    Admin updateAdmin(Admin admin);
    boolean deleteAdmin(int id);
}
