package application.photocontest.repository.contracts;


import application.photocontest.models.*;
import application.photocontest.repository.contracts.genericrepository.CudRepositoryOperations;
import application.photocontest.repository.contracts.genericrepository.GetRepositoryOperations;

import java.util.List;
import java.util.Optional;


public interface ContestRepository extends GetRepositoryOperations<Contest>, CudRepositoryOperations<Contest> {

    Contest getByTitle(String title);

    List<Contest> getOngoingContests();

    List<Contest> getVotingContests();

    List<Contest> getFinishedContests();

    Contest getContestByImageUploaderId(int contestId,int userId);

    List<Contest> getByUserId(int id);

    List<Contest> getUserContests(int id);

    List<Contest> search(Optional<String> phase);

    List<Contest> searchAsUser(Optional<String> phase);

}
