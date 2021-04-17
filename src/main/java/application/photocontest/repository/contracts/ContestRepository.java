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

    List<User> getContestParticipants(int contestId);

    List<Image> getContestImages(int contestId);

    Contest getContestByImageUploaderId(int contestId, int userId);

    List<Contest> getByUserId(int id);

    List<Contest> getUserContests(int id, Optional<String> phase);

    List<Contest> getUserJuryVotingContests(int userId);
}
