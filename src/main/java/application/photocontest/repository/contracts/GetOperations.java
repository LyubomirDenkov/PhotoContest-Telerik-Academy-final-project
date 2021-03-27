package application.photocontest.repository.contracts;

import java.util.List;

public interface GetOperations<T> {
    List<T> getAll();

    T getById(int id);
}
