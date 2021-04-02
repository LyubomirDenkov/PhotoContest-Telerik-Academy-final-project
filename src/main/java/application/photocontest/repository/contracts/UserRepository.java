package application.photocontest.repository.contracts;


import application.photocontest.models.*;

import java.util.List;

public interface UserRepository extends GetRepositoryOperations<User>, CudRepositoryOperations<User> {
    UserCredentials getByUserName(String userName);

    User getUserByUserName(String userName);

    Role getRoleByName(String name);

    List<User> getParticipantsFromContest(int id);

}
