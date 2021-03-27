package application.photocontest.repository.contracts;

public interface CudRepositoryOperations<T> {
    T create(T name);

    T update(T name);

    T delete(int id);

}
