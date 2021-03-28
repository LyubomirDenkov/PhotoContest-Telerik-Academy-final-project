package application.photocontest.service.contracts;

import application.photocontest.models.User;

public interface UserService extends GetServiceOperations<User> {
    User getByUserName(String userName);

    User getByEmail(String email);

    User create(User user);

    User update(User user,User userToUpdate);

    void delete(User type,int id);
}
