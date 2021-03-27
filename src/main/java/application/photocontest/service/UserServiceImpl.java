package application.photocontest.service;

import application.photocontest.enums.UserRoles;
import application.photocontest.models.User;
import application.photocontest.repository.contracts.UserRepository;
import application.photocontest.service.contracts.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> getAll() {
        return null;
    }

    @Override
    public User getById(int id) {
        return null;
    }

    @Override
    public User create(User name) {
        return null;
    }

    @Override
    public User update(User name) {
        return null;
    }

    @Override
    public User delete(int id) {
        return null;
    }
}
