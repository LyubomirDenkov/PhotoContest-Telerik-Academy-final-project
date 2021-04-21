package application.photocontest.services;

import application.photocontest.exceptions.DuplicateEntityException;
import application.photocontest.exceptions.EntityNotFoundException;
import application.photocontest.exceptions.UnauthorizedOperationException;
import application.photocontest.models.*;
import application.photocontest.repository.contracts.*;
import application.photocontest.service.ContestServiceImpl;
import application.photocontest.service.contracts.ImageService;
import application.photocontest.service.contracts.ImgurService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.*;

import static application.photocontest.Helpers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ContestServiceImplTests {

    @Mock
    ContestRepository contestRepository;

    @Mock
    TypeRepository typeRepository;

    @Mock
    UserRepository userRepository;

    @Mock
    ImgurService imgurService;

    @Mock
    PointsRepository pointsRepository;

    @Mock
    ImageRepository imageRepository;

    @Mock
    ImageService imageService;

    @Mock
    NotificationRepository notificationRepository;

    @Mock
    ImageReviewRepository imageReviewRepository;


    @InjectMocks
    ContestServiceImpl contestService;


    @Test
    public void getAll_Should_ReturnAllContests_When_IsCalled() {

        List<Contest> result = new ArrayList<>();
        User organizer = createMockOrganizer();

        when(contestService.getAll(organizer, Optional.empty()))
                .thenReturn(result);

        contestService.getAll(organizer, Optional.empty());

        verify(contestRepository, times(1)).getAll();
    }


    @Test
    public void getAll_Should_Throw_When_IsCalledFromUserWithoutPhaseParameter() {

        User user = createMockUser();

        Assertions.assertThrows(UnauthorizedOperationException.class,
                () -> contestService.getAll(user, Optional.empty()));


    }


    @Test
    public void getAll_Should_ReturnOngoing_When_PhaseIsCalledOngoing() {

        User user = createMockOrganizer();
        boolean isOrganizer = true;
        List<Contest> contests = new ArrayList<>();

        when(contestRepository.getOngoingContests(isOrganizer)).thenReturn(contests);

        contestService.getAll(user, Optional.of("ongoing"));

        verify(contestRepository, times(1)).getOngoingContests(isOrganizer);

    }

    @Test
    public void getAll_Should_ReturnVoting_When_PhaseIsCalledVoting() {

        User user = createMockOrganizer();
        boolean isOrganizer = true;
        List<Contest> contests = new ArrayList<>();

        when(contestRepository.getVotingContests(user.getId(), isOrganizer)).thenReturn(contests);

        contestService.getAll(user, Optional.of("voting"));

        verify(contestRepository, times(1)).getVotingContests(user.getId(), isOrganizer);

    }

    @Test
    public void getAll_Should_ReturnFinished_When_PhaseIsCalledFinished() {

        User user = createMockOrganizer();
        boolean isOrganizer = true;
        List<Contest> contests = new ArrayList<>();

        when(contestRepository.getFinishedContests(user.getId(), isOrganizer)).thenReturn(contests);

        contestService.getAll(user, Optional.of("finished"));

        verify(contestRepository, times(1)).getFinishedContests(user.getId(), isOrganizer);

    }

    @Test
    public void getAll_Should_Throw_When_UserDontHaveEnoughPointsToCanSeeVotingContests() {

        User user = createMockUser();
        user.setPoints(Set.of(new Points(1, 22)));


        Assertions.assertThrows(UnauthorizedOperationException.class,
                () -> contestService.getAll(user, Optional.of("voting")));

    }

    @Test
    public void getAll_Should_Throw_When_PhaseIsNotValid() {

        User user = createMockOrganizer();


        Assertions.assertThrows(UnsupportedOperationException.class,
                () -> contestService.getAll(user, Optional.of("something")));

    }

    @Test
    public void getByUserId_Should_ReturnContest_When_Exist() {

        List<Contest> contests = new ArrayList<>();

        when(contestRepository.getByUserId(1)).thenReturn(contests);

        contestService.getByUserId(1);

        verify(contestRepository, times(1)).getByUserId(1);

    }


    @Test
    public void removeImageFromContest_Should_ThrowException_When_UserIsNotJury() {

        User user = createMockUser();
        Contest contest = createMockContest();
        Image image = createMockImage();
        contest.setJury(new HashSet<>());


        when(contestRepository.getById(1)).thenReturn(contest);

        when(imageRepository.getById(1)).thenReturn(image);

        Assertions.assertThrows(UnauthorizedOperationException.class,
                () -> contestService.removeImageFromContest(user, contest.getId(), image.getId()));


    }

    @Test
    public void removeImageFromContest_Should_RemoveImage_When_UserIsJury() {

        User user = createMockOrganizer();
        Contest contest = createMockContest();
        Image image = createMockImage();
        contest.setJury(Set.of(user));
        contest.setImages(new HashSet<>());
        contest.setWinnerImages(new HashSet<>());


        when(contestRepository.getById(1)).thenReturn(contest);

        when(imageRepository.getById(1)).thenReturn(image);


        contestService.removeImageFromContest(user, contest.getId(), image.getId());

        verify(contestRepository, times(1)).update(contest);

    }


    @Test
    public void mainPageOngoingContest_Should_Return_When_IsCall() {


        List<Contest> contestList = new ArrayList<>();


        when(contestRepository.mainPageOngoingContests()).thenReturn(contestList);


        contestService.mainPageOngoingContest();


        verify(contestRepository, times(1)).mainPageOngoingContests();

    }

    @Test
    public void getAllTypes_Should_Return_When_IsCall() {


        List<Type> types = new ArrayList<>();


        when(typeRepository.getAll()).thenReturn(types);


        contestService.getAllTypes();


        verify(typeRepository, times(1)).getAll();

    }


    @Test
    public void getContestImages_Should_ThrowException_When_NotAuthenticated() {

        User user = createMockUser();
        user.setRoles(new HashSet<>());

        Assertions.assertThrows(UnauthorizedOperationException.class,
                () -> contestService.getContestImages(user, 1));

    }

    @Test
    public void getContestImages_Should_ThrowException_When_UserIsNotParticipatedInContest() {

        User user = createMockUser();
        Contest contest = createMockContest();
        contest.setParticipants(new HashSet<>());
        contest.setPhase(new Phase(1, "finished"));

        when(contestRepository.getById(1)).thenReturn(contest);

        Assertions.assertThrows(UnauthorizedOperationException.class,
                () -> contestService.getContestImages(user, 1));

    }

    @Test
    public void getContestImages_Should_ReturnImages_When_UserIsJury() {

        User user = createMockOrganizer();
        Contest contest = createMockContest();

        when(contestRepository.getById(1)).thenReturn(contest);

        contestService.getContestImages(user, 1);

        verify(contestRepository, times(1)).getContestImages(1);
    }


    @Test
    public void getAll_Should_Throw_When_UserIsNotOrganizer() {


        User user = createMockUser();
        user.setRoles(new HashSet<>());


        Assertions.assertThrows(UnauthorizedOperationException.class,
                () -> contestService.getAll(user, Optional.empty()));
    }

    @Test
    public void getById_Should_Throw_When_Not_Exist() {

        User organizer = createMockOrganizer();


        when(contestRepository.getById(254)).thenThrow(EntityNotFoundException.class);

        Assertions.assertThrows(EntityNotFoundException.class,
                () -> contestService.getById(organizer, 254));
    }


    @Test
    public void getById_Should_Return_When_Exist() {

        User organizer = createMockOrganizer();
        Contest contest = createMockContest();


        when(contestRepository.getById(1)).thenReturn(contest);

        contestService.getById(organizer, 1);

        Mockito.verify(contestRepository, Mockito.times(1)).getById(1);
    }


    @Test
    public void getVoting_Should_Throw_When_UserWithLessPoints() {
        User user = createMockUser();
        user.setPoints(Set.of(new Points(2, 50)));


        Assertions.assertThrows(UnauthorizedOperationException.class,
                () -> contestService.getAll(user, Optional.of("voting")));
    }

    @Test
    public void getByUserId_Should_Return_When_IsCalled() {
        List<Contest> result = new ArrayList<>();
        User user = createMockUser();


        when(contestRepository.getByUserId(user.getId())).thenReturn(result);

        contestService.getByUserId(user.getId());

        Mockito.verify(contestRepository, Mockito.times(1)).getByUserId(user.getId());
    }

    @Test
    public void getAllTypes_Should_Return_When_IsCalled() {
        List<Type> result = new ArrayList<>();

        when(typeRepository.getAll()).thenReturn(result);

        contestService.getAllTypes();

        Mockito.verify(typeRepository, Mockito.times(1)).getAll();
    }

    @Test
    public void create_Should_Throw_When_DuplicateName() {
        Contest contest = createMockContest();
        Set<Integer> jury = Set.of(1, 2, 3);
        Set<Integer> participants = Set.of(4, 5, 6);

        when(contestRepository.getByTitle(contest.getTitle())).thenReturn(contest);

        Assertions.assertThrows(DuplicateEntityException.class,
                () -> contestService.create(contest.getUser(), contest, jury, participants, Optional.empty(), Optional.empty()));
    }

    @Test
    public void create_Should_Create_When_ValidationsOk() throws IOException {
        Contest contest = createMockContest();
        contest.setParticipants(new HashSet<>());
        contest.setJury(new HashSet<>());
        User user = createMockUser();
        User organizer = createMockOrganizer();
        User userParticipant = createMockUser();
        userParticipant.setId(5);
        Set<Integer> jury = Set.of(user.getId());
        Set<Integer> participants = Set.of(userParticipant.getId());

        Mockito.when(contestRepository.getByTitle(contest.getTitle())).thenThrow(EntityNotFoundException.class);

        Mockito.when(userRepository.getOrganizers()).thenReturn(List.of(organizer));

        Mockito.when(userRepository.getById(user.getId())).thenReturn(user);

        Mockito.when(imgurService.uploadImageToImgur(Optional.empty(), Optional.empty())).thenReturn("");

        Mockito.when(userRepository.getById(userParticipant.getId())).thenReturn(userParticipant);

        Mockito.when(contestRepository.create(contest)).thenReturn(contest);

        contestService.create(organizer, contest, jury, participants, Optional.empty(), Optional.empty());

        Mockito.verify(contestRepository, Mockito.times(1)).create(contest);
    }

    @Test
    public void update_Should_Throw_When_UserIsNotAuthorized() {
        Contest contest = createMockContest();
        User organizer = createMockOrganizer();
        contest.setUser(organizer);
        User user = createMockUser();
        Set<Integer> jury = Set.of(1, 2, 3);
        Set<Integer> participants = Set.of(4, 5, 6);


        Assertions.assertThrows(UnauthorizedOperationException.class,
                () -> contestService.update(user, contest, jury, participants, Optional.empty(), Optional.empty()));

    }

    @Test
    public void update_Should_Update_When_ValidationsOk() throws IOException {
        Contest contest = createMockContest();
        contest.setParticipants(new HashSet<>());
        contest.setJury(new HashSet<>());
        User user = createMockUser();
        User organizer = createMockOrganizer();
        User userParticipant = createMockUser();
        Set<Integer> jury = new HashSet<>();
        Set<Integer> participants = Set.of(userParticipant.getId());
        Notification notification = createMockNotification();
        notification.setUser(user);


        Mockito.when(userRepository.getOrganizers()).thenReturn(List.of(organizer));

        Mockito.when(userRepository.getById(user.getId())).thenReturn(user);

        contestService.update(organizer, contest, jury, participants, Optional.empty(), Optional.empty());

        Mockito.verify(contestRepository, Mockito.times(1)).update(contest);

    }

    @Test
    public void update_Should_UpdatePoints_When_ValidationsOk() throws IOException {
        Contest contest = createMockContest();
        User user = createMockUser();
        User organizer = createMockOrganizer();
        User userParticipant = createMockUser();
        Set<Integer> jury = Set.of(user.getId());
        Set<Integer> participants = Set.of(userParticipant.getId());

        Points points = new Points(6, 100);


        Mockito.when(userRepository.getOrganizers()).thenReturn(List.of(organizer));

        Mockito.when(userRepository.getById(user.getId())).thenReturn(user);

        pointsRepository.update(points);

        Mockito.verify(pointsRepository, Mockito.times(1)).update(points);

        userRepository.update(user);

        Mockito.verify(userRepository, Mockito.times(1)).update(user);

        contestService.update(organizer, contest, jury, participants, Optional.empty(), Optional.empty());

        Mockito.verify(contestRepository, Mockito.times(1)).update(contest);

    }


    @Test
    public void rateImage_Should_Throw_When_RatingTwice() {
        Contest contest = createMockContest();
        User user = createMockUser();
        Image image = createMockImage();
        ImageReview imageReview = createMockImageReview();


        Mockito.when(contestRepository.getById(contest.getId())).thenReturn(contest);

        Mockito.when(imageRepository.getById(image.getId())).thenReturn(image);

        Assertions.assertThrows(UnauthorizedOperationException.class,
                () -> contestService.rateImage(user, imageReview, contest.getId(), image.getId()));

    }

    @Test
    public void rateImage_Should_ThrowException_When_UserIsNotJury() {
        Contest contest = createMockContest();
        Phase phase = new Phase();
        phase.setId(2);
        phase.setName("voting");
        contest.setPhase(phase);
        User user = createMockUser();
        Image image = createMockImage();
        ImageReview imageReview = createMockImageReview();


        Mockito.when(contestRepository.getById(contest.getId())).thenReturn(contest);

        Mockito.when(imageRepository.getById(image.getId())).thenReturn(image);

        Assertions.assertThrows(UnauthorizedOperationException.class,
                () -> contestService.rateImage(user, imageReview, contest.getId(), image.getId()));

    }

    @Test
    public void rateImage_Should_Rate_When_ValidationsOk() {
        Contest contest = createMockContest();
        Phase phase = new Phase();
        phase.setId(2);
        phase.setName("voting");
        contest.setPhase(phase);
        User user = createMockUser();
        contest.setJury(Set.of(user));
        Image image = createMockImage();
        contest.setImages(Set.of(image));
        ImageReview imageReview = createMockImageReview();
        User organizer = createMockOrganizer();

        ImageReview testImageReview = new ImageReview();
        testImageReview.setUser(organizer);
        testImageReview.setImage(image);
        testImageReview.setContest(contest);
        testImageReview.setPoints(5);
        testImageReview.setComment("comment");


        Mockito.when(contestRepository.getById(contest.getId())).thenReturn(contest);

        Mockito.when(imageRepository.getById(image.getId())).thenReturn(image);

        Mockito.when(imageReviewRepository.getImageReviewUserContestAndImageId(user.getId(), contest.getId(), image.getId()))
                .thenThrow(EntityNotFoundException.class);

        Mockito.when(imageReviewRepository.create(imageReview)).thenReturn(imageReview);

        contestService.rateImage(user, imageReview, contest.getId(), image.getId());

        verify(imageReviewRepository, times(1)).create(imageReview);


    }

    @Test
    public void addImage_Should_Add_When_ValidationsOk() {
        Contest contest = createMockContest();
        User user = createMockUser();
        contest.setJury(Set.of(user));
        Image image = createMockImage();


        Mockito.when(contestRepository.getById(contest.getId())).thenReturn(contest);

        Mockito.when(imageRepository.getById(image.getId())).thenReturn(image);

        contestService.addImageToContest(user, contest.getId(), image.getId());

        Mockito.verify(contestRepository, Mockito.times(1)).update(contest);

    }

    @Test
    public void addUser_Should_Add_When_ValidationsOk() {
        Contest contest = createMockContest();
        User user = createMockUser();
        Set<User> userToAdd = new HashSet<>();
        contest.setParticipants(userToAdd);


        Mockito.when(contestRepository.getById(contest.getId())).thenReturn(contest);

        Mockito.when(userRepository.getById(user.getId())).thenReturn(user);

        contestService.joinContest(user, contest.getId(), user.getId());

        Mockito.verify(contestRepository, Mockito.times(1)).update(contest);

    }

    @Test
    public void uploadImage_Should_Upload_When_ValidationsOk() throws IOException {
        Contest contest = createMockContest();
        User user = createMockUser();
        Image image = createMockImage();
        image.setUploader(user);
        user.setImages(Set.of(image));

        Mockito.when(contestRepository.getById(contest.getId())).thenReturn(contest);

        Mockito.when(contestRepository.getContestByImageUploaderId(contest.getId(), user.getId())).thenThrow(EntityNotFoundException.class);

        Mockito.when(imageService.create(user, image, Optional.empty(), Optional.of(image.getUrl()))).thenReturn(image);

        contestService.uploadImageToContest(user, image, contest.getId(), Optional.empty(), Optional.of(image.getUrl()));

        Mockito.verify(contestRepository, Mockito.times(1)).update(contest);

    }


    @Test
    public void getContestParticipants_Should_Throw_When_User_NotAuthorized() {

        User user = createMockUser();
        Contest contest = createMockContest();

        Assertions.assertThrows(UnauthorizedOperationException.class,
                () -> contestService.getContestParticipants(user, contest.getId()));
    }

    @Test
    public void getContestParticipants_Should_Return_When_User_IsCalled() {

        User organizer = createMockOrganizer();
        Contest contest = createMockContest();

        contestService.getContestParticipants(organizer, contest.getId());

        Mockito.verify(contestRepository, times(1)).getContestParticipants(contest.getId());

    }

    @Test
    public void removeImageFromContest_Should_Throw_When_User_NotAuthorized() {

        User user = createMockUser();
        Set<Role> roles = new HashSet<>();
        user.setRoles(roles);
        Contest contest = createMockContest();
        Image image = createMockImage();

        Assertions.assertThrows(UnauthorizedOperationException.class,
                () -> contestService.removeImageFromContest(user, contest.getId(), image.getId()));
    }

    @Test
    public void removeImageFromContest_Should_Remove_When_ValidationsOk() {

        User organizer = createMockOrganizer();
        Contest contest = createMockContest();
        Image image = createMockImage();
        contest.setWinnerImages(new HashSet<>());
        contest.setImages(new HashSet<>());

        Mockito.when(contestRepository.getById(contest.getId())).thenReturn(contest);

        Mockito.when(imageRepository.getById(image.getId())).thenReturn(image);

        contestService.removeImageFromContest(organizer, contest.getId(), image.getId());

        Mockito.verify(contestRepository, times(1)).update(contest);

    }


}
