package application.photocontest.service.contracts;

public interface CudServiceOperations<T> {
    T create(T name);

    T update(T name);

    void delete(int id);

}