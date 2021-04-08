package application.photocontest.service.contracts;

import application.photocontest.models.User;
import application.photocontest.models.UserCredentials;

import java.util.List;

public interface GetServiceOperations<T> {
    List<T> getAll();

    T getById(User user, int id);
}
