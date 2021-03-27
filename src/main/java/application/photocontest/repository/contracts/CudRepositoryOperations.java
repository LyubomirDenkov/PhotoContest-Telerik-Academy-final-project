package application.photocontest.repository.contracts;

public interface CudRepositoryOperations<T> {
    T create(T type);

    T update(T type);

    void delete(int id);

}
