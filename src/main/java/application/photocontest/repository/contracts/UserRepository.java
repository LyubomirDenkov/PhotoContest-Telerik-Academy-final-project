package application.photocontest.repository.contracts;


import application.photocontest.models.Rank;
import application.photocontest.models.Role;
import application.photocontest.models.User;

public interface UserRepository extends GetRepositoryOperations<User>, CudRepositoryOperations<User> {
    User getByUserName(String userName);

    User getByEmail(String email);

    Rank getRankByName(String name);

    Role getRoleByName(String name);

}
