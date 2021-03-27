package application.photocontest.service.contracts;

import java.util.List;

public interface GetServiceOperations<T> {
    List<T> getAll();

    T getById(int id);
}
