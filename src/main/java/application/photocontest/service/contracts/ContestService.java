package application.photocontest.service.contracts;


import application.photocontest.models.*;
import application.photocontest.models.dto.UserActionsDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.Set;


public interface ContestService  {

    List<Contest> getAll(User user,Optional<String> phase);

    Contest getById(User user, int id);

    Contest create(User user, Contest contest, Set<Integer> jurySet, Set<Integer> participantSet, Optional<MultipartFile> file, Optional<String> url) throws IOException;

    Contest update(User user, Contest contest, Set<Integer> jurySet, Set<Integer> participantsSet, Optional<MultipartFile> file, Optional<String> url) throws IOException;

    Image uploadImageToContest(User user, Image image, int contestId, Optional<MultipartFile> file, Optional<String> url) throws IOException;

    List<Image> getContestImages(User user, int contestId);

    List<User> getContestParticipants(User user, int contestId);

    void joinContest(User user, int contestId, int userId);

    ImageReview rateImage(User user, ImageReview imageReview, int contestId, int imageId);

    Image addImageToContest(User user, int contestId, int imageId);

    List<Type> getAllTypes();

    List<Contest> getByUserId(int id);

    void removeImageFromContest(User user, int contestId, int imageId);

    List<Contest> mainPageOngoingContest();

    UserActionsDto getUserActionsDto(User user, Contest contest);
}
