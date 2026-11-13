package healthandbike.services;

import healthandbike.domain.User;

import java.util.List;

public interface UserAdminService {

    List<User> getAllUsers();
    boolean deleteUser(int id);
}
