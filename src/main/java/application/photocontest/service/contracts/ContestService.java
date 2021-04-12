package application.photocontest.service.contracts;


import application.photocontest.models.*;
import application.photocontest.service.contracts.genericservice.GetServiceOperations;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.Set;


public interface ContestService extends GetServiceOperations<Contest> {

    Contest create(User user, Contest contest, Set<Integer> jurySet, Set<Integer> participantSet, Optional<MultipartFile> file, Optional<String> url) throws IOException;

    Contest update(User user, Contest contest, Set<Integer> jurySet, Set<Integer> participantsSet, Optional<MultipartFile> file, Optional<String> url) throws IOException;

    Image uploadImageToContest(User user,Image image, int contestId,Optional<MultipartFile> file,Optional<String> url) throws IOException;

    void delete(User user, int id);

    void addUserToContest(User user, int contestId, int userId);

    void rateImage(User user, int contestId, int imageId, int points, String comment);

    Image addImage(User user, int contestId, int imageId);

    List<Contest> getOngoingContests();

    List<Type> getAllTypes();

    Set<Image> getContestImages(int id);

    List<Contest> getFinishedContests();

    List<Contest> getVotingContests();
}
