package application.photocontest.repository.contracts;


import application.photocontest.models.Contest;
import application.photocontest.models.Phase;
import application.photocontest.models.Type;
import application.photocontest.models.User;

import java.util.List;


public interface ContestRepository extends GetRepositoryOperations<Contest>,CudRepositoryOperations<Contest> {
    Contest getByTitle(String title);

    List<User> getContestJury();

    List<Type> getAllTypes();

    Type getTypeById(int id);

    Phase getPhaseByName(String phaseName);

    List<Contest> getOngoingContests();

    List<Contest> getFinishedContests();
}
