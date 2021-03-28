package application.photocontest.service.contracts;

import application.photocontest.models.User;

import java.util.List;

public interface GetServiceOperations<T> {
    List<T> getAll(User user);

    T getById(User user, int id);
}
