package application.photocontest.service.contracts;

import application.photocontest.models.User;

public interface UserService extends GetServiceOperations<User>, CudServiceOperations<User> {
    User getByUserName(String userName);

    User getByEmail(String email);
}
