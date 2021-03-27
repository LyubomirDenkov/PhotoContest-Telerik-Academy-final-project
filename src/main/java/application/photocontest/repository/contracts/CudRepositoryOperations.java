package application.photocontest.repository.contracts;

public interface CudRepositoryOperations<T> {
    T create(T name);

    T update(T name);

    void delete(int id);

}
