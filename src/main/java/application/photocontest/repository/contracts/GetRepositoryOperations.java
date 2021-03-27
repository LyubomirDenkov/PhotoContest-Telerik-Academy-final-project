package application.photocontest.repository.contracts;

import java.util.List;

public interface GetRepositoryOperations<T> {
    List<T> getAll();

    T getById(int id);
}
