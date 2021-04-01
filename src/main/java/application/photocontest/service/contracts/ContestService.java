package application.photocontest.service.contracts;


import application.photocontest.models.Contest;
import application.photocontest.models.Organizer;
import application.photocontest.models.UserCredentials;
import application.photocontest.models.dto.ContestDto;


public interface ContestService extends GetServiceOperations<Contest> {

    Contest create(Organizer organizer, Contest contest);

    Contest update(Organizer organizer, Contest contest, ContestDto contestDto);

    void delete(Organizer organizer,int id);

    void addUserToContest(UserCredentials user, int contestId, int userId);

    void rateImage(UserCredentials user, int contestId, int imageId, int points, String comment);
}
