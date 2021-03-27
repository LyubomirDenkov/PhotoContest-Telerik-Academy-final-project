package application.photocontest.service.contracts;

public interface CudOperations<T> {
    T create(T name);

    T update(T name);

    T delete(int id);

}