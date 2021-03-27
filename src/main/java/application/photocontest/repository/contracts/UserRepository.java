package application.photocontest.repository.contracts;


import application.photocontest.models.User;

public interface UserRepository extends GetRepositoryOperations<User>, CudRepositoryOperations<User> {

}
