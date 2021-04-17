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

    Image uploadImageToContest(User user, Image image, int contestId, Optional<MultipartFile> file, Optional<String> url) throws IOException;

    List<User> getContestParticipants(User user, int contestId);

    void addUserToContest(User user, int contestId, int userId);

    ImageReview rateImage(User user, ImageReview imageReview, int contestId, int imageId, int points, String comment);

    Image addImageToContest(User user, int contestId, int imageId);

    List<Contest> getOngoingContests();

    List<Type> getAllTypes();

    List<Contest> getFinishedContests(User user);

    List<Contest> getVotingContests(User user);

    List<Contest> getByUserId(int id);

    List<Contest> search(User user, Optional<String> phase);

    void removeImageFromContest(User user,int contestId,int imageId);

}
