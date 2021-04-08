package application.photocontest.service.contracts;


import application.photocontest.models.*;
import application.photocontest.models.dto.ContestDto;

import java.util.List;
import java.util.Set;


public interface ContestService extends GetServiceOperations<Contest> {

    Contest create(User user, Contest contest, Set<Integer> jurySet, Set<Integer> participantSet);

    Contest update(User user, Contest contest, Set<Integer> jurySet, Set<Integer> participantsSet);

    void delete(User user, int id);

    void addUserToContest(User user, int contestId, int userId);

    void rateImage(User user, int contestId, int imageId, int points, String comment);

    Image addImage(User user, int contestId, int imageId);

    List<Contest> getOngoingContests();

    List<Contest> getFinishedContests();
}
