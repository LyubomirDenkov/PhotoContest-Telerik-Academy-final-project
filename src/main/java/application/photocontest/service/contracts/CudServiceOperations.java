package application.photocontest.service.contracts;

public interface CudServiceOperations<T> {
    T create(T type);

    T update(T type);

    void delete(int id);

}