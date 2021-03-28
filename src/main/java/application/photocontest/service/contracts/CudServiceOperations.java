package application.photocontest.service.contracts;

import application.photocontest.models.User;

public interface CudServiceOperations<T> {
    T create(User user, T type);

    T update(User user,T secondType);

    void delete(User user,int id);

}