package application.photocontest.service.contracts.genericservice;

import application.photocontest.models.User;


import java.util.List;

public interface GetServiceOperations<T> {
    List<T> getAll();

    T getById(User user, int id);
}
