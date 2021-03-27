package application.photocontest.service;

import application.photocontest.models.User;
import application.photocontest.service.contracts.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserService userService;

    @Autowired
    public UserServiceImpl(UserService userService) {
        this.userService = userService;
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

    @Override
    public List<User> getAll() {
        return null;
    }

    @Override
    public User getById(int id) {
        return null;
    }
}
