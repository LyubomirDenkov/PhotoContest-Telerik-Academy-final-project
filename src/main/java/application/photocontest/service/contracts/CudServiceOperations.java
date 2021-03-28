package application.photocontest.service.contracts;

public interface CudServiceOperations<T> {
    T create(T type);

    T update(T type,int id);

    void delete(T type,int id);

}