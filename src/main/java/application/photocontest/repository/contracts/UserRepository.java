package application.photocontest.repository.contracts;


import application.photocontest.models.*;
import application.photocontest.repository.contracts.genericrepository.CudRepositoryOperations;
import application.photocontest.repository.contracts.genericrepository.GetRepositoryOperations;

import java.util.List;

public interface UserRepository extends GetRepositoryOperations<User>, CudRepositoryOperations<User> {
    User getUserByUserName(String userName);

    List<User> getLeaderboard();

    Role getRoleByName(String name);

    List<User> getParticipantsFromContest(int id);

    User getUserByPictureId(int id);

    List<User> getOrganizers();

    List<User> getAllPotentialJury();

    List<User> getAllUsers();

}
