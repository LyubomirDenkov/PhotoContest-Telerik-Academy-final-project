package application.photocontest.repository.contracts;


import application.photocontest.models.*;

public interface UserRepository extends GetRepositoryOperations<User>, CudRepositoryOperations<User> {
    UserCredentials getByUserName(String userName);

    User getUserByUserName(String userName);

    Organizer getOrganizerByUserName(String userName);

    User getByEmail(String email);

    Rank getRankByName(String name);

    Role getRoleByName(String name);

}
