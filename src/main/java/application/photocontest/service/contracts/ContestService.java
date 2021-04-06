package application.photocontest.service.contracts;


import application.photocontest.models.*;
import application.photocontest.models.dto.ContestDto;

import java.util.List;


public interface ContestService extends GetServiceOperations<Contest>{

    Contest create(User user, Contest contest, ContestDto contestDto);

    Contest update(User user, Contest contest, ContestDto contestDto);

    void delete(User user,int id);

    void addUserToContest(User user, int contestId, int userId);

    void rateImage(User user, int contestId, int imageId, int points, String comment);

    Image addImage(User user, int contestId, int imageId);

    List<Contest> getOngoingContests();
}
