package application.photocontest.service.contracts;

import application.photocontest.models.User;
import application.photocontest.models.UserCredentials;

import java.util.List;

public interface UserService extends GetServiceOperations<User> {

    User getUserByUserName(String userName);

    User create(User user);

    User update(User user,User userToUpdate);

    void delete(User user,int id);

}
