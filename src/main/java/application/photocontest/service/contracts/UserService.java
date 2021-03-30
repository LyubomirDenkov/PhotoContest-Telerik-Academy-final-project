package application.photocontest.service.contracts;

import application.photocontest.models.User;
import application.photocontest.models.UserCredentials;

public interface UserService extends GetServiceOperations<User> {
    UserCredentials getByUserName(String userName);

    User getUserByUserName(String userName);

    User create(User user);

    User update(UserCredentials userCredentials,User userToUpdate);

    void delete(UserCredentials userCredentials,int id);
}
