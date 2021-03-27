package application.photocontest.service.contracts;

import application.photocontest.models.User;

public interface UserService extends GetServiceOperations<User>, CudServiceOperations<User> {
}
