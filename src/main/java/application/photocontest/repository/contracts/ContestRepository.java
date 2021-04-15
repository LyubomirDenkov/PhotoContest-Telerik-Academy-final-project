package application.photocontest.repository.contracts;


import application.photocontest.models.*;
import application.photocontest.repository.contracts.genericrepository.CudRepositoryOperations;
import application.photocontest.repository.contracts.genericrepository.GetRepositoryOperations;

import java.util.List;


public interface ContestRepository extends GetRepositoryOperations<Contest>, CudRepositoryOperations<Contest> {

    Contest getByTitle(String title);

    List<Contest> getOngoingContests();

    List<Contest> getVotingContests();

    List<Contest> getFinishedContests();

    Contest getContestByImageUploaderId(int id);

    List<Contest> getByUserId(int id);

    List<Contest> getUserContests(int id);

}
