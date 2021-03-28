package application.photocontest.service.contracts;

import application.photocontest.models.User;

public interface CudServiceOperations<T> {
    T create(User user, T type);

    T update(T type,T secondType);

    void delete(T type,int id);

}