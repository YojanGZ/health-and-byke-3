package healthandbike.services;

import healthandbike.domain.Admin;
import healthandbike.domain.User;
import healthandbike.repository.UserRepository;

import java.util.List;
import java.util.Optional;

public class AdminServiceImpl implements AdminService, UserAdminService {

    private final UserRepository userRepository;

    public AdminServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Admin createAdmin(Admin admin) {
        return userRepository.saveAdmin(admin);
    }

    @Override
    public Optional<Admin> getAdminById(int id) {
        return userRepository.findAdminById(id);
    }

    @Override
    public Optional<Admin> getAdminByEmail(String email) {
        return userRepository.findAdminByEmail(email);
    }

    @Override
    public List<Admin> getAllAdmins() {
        return userRepository.findAllAdmins();
    }

    @Override
    public Admin updateAdmin(Admin admin) {
        return userRepository.saveAdmin(admin);
    }

    @Override
    public boolean deleteAdmin(int id) {
        return false;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAllUsers();
    }

    @Override
    public boolean deleteUser(int id) {
        return userRepository.deleteUser(id);
    }
}
