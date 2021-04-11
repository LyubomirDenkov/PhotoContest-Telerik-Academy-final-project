package application.photocontest.repository.contracts;


import application.photocontest.models.*;

import java.util.List;


public interface ContestRepository extends GetRepositoryOperations<Contest>,CudRepositoryOperations<Contest> {
    Contest getByTitle(String title);

    List<User> getContestJury();

    List<Contest> getOngoingContests();

    List<Contest> getFinishedContests();

}
