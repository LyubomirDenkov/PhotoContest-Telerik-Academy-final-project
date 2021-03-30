package application.photocontest.service.contracts;


import application.photocontest.models.UserCredentials;

public interface CudServiceOperations<T> {
    T create(UserCredentials userCredentials, T type);

    T update(UserCredentials userCredentials,T type);

    void delete(UserCredentials userCredentials,int id);

}