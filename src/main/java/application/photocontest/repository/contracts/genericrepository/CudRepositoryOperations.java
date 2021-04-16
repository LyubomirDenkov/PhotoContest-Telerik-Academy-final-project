package application.photocontest.repository.contracts.genericrepository;

public interface CudRepositoryOperations<T> {
    T create(T type);

    T update(T type);


}
