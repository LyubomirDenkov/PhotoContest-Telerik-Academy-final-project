package application.photocontest.service;

import application.photocontest.enums.UserRoles;
import application.photocontest.exceptions.DuplicateEntityException;
import application.photocontest.exceptions.EntityNotFoundException;
import application.photocontest.models.*;
import application.photocontest.repository.contracts.ContestRepository;
import application.photocontest.repository.contracts.NotificationRepository;
import application.photocontest.repository.contracts.PointsRepository;
import application.photocontest.repository.contracts.UserRepository;
import application.photocontest.service.contracts.ImgurService;
import application.photocontest.service.contracts.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

import static application.photocontest.constants.Constants.*;
import static application.photocontest.service.authorization.AuthorizationHelper.verifyIsUserOwnAccount;
import static application.photocontest.service.authorization.AuthorizationHelper.*;
import static application.photocontest.service.helper.NotificationHelper.sendMessageWhenUserCreated;

@Service
public class UserServiceImpl implements UserService {


    private final UserRepository userRepository;
    private final ImgurService imgurService;
    private final ContestRepository contestRepository;
    private final PointsRepository pointsRepository;
    private final NotificationRepository notificationRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, ImgurService imgurService, ContestRepository contestRepository, PointsRepository pointsRepository, NotificationRepository notificationRepository) {
        this.userRepository = userRepository;
        this.imgurService = imgurService;
        this.contestRepository = contestRepository;
        this.pointsRepository = pointsRepository;
        this.notificationRepository = notificationRepository;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.getAllUsers();
    }

    @Override
    public List<Contest> getUserContests(User user, int userId, Optional<String> phase) {

        verifyUserHasRoles(user, UserRoles.USER, UserRoles.ORGANIZER);

        if (!user.isOrganizer()) {
            verifyIsUserOwnAccount(user.getId(), userId, USER_CAN_ACCESS_ONLY_HIS_OWN_ACCOUNT_ERROR_MESSAGE);
        }

        return contestRepository.getUserContests(user.getId(), phase);
    }

    @Override
    public List<Notification> getUserNotifications(User user, int id) {

        verifyUserHasRoles(user, UserRoles.USER, UserRoles.ORGANIZER);

        verifyIsUserOwnAccount(user.getId(), id, SELF_NOTIFICATIONS_ERROR_MESSAGE);

        return notificationRepository.getAll(id);
    }

    @Override
    public List<User> getAll(User user) {
        verifyUserHasRoles(user, UserRoles.ORGANIZER);
        return userRepository.getAll();
    }

    @Override
    public User getById(User user, int id) {

        verifyUserHasRoles(user, UserRoles.USER, UserRoles.ORGANIZER);

        if (!user.isOrganizer()) {
            verifyIsUserOwnAccount(user.getId(), id, USER_CAN_ACCESS_ONLY_HIS_OWN_ACCOUNT_ERROR_MESSAGE);
        }

        return userRepository.getById(id);
    }

    @Override
    public User getUserByUserName(String userName) {
        return userRepository.getUserByUserName(userName);
    }

    @Override
    public List<User> getLeaderboard(User user) {

        verifyUserHasRoles(user, UserRoles.USER, UserRoles.ORGANIZER);

        List<User> leaderboard = userRepository.getLeaderboard();

        if (leaderboard.size() > 20) {
            return leaderboard.subList(0, 20);
        }

        return leaderboard;
    }


    @Override
    public User create(User user, Optional<MultipartFile> file, Optional<String> url) throws IOException {

        boolean isUserNameExist = true;

        try {
            userRepository.getUserByUserName(user.getUserCredentials().getUserName());
        } catch (EntityNotFoundException e) {
            isUserNameExist = false;
        }

        if (isUserNameExist) {
            throw new DuplicateEntityException(USERNAME_ALREADY_EXIST_ERROR_MESSAGE);
        }

        String profileImageUrl = imgurService.uploadImageToImgur(file, url);

        if (profileImageUrl.isBlank()) {
            user.setProfileImage(INITIAL_PROFILE_IMAGE);
        } else {
            user.setProfileImage(profileImageUrl);
        }

        User newRegisteredUser = userRepository.create(user);
        addRoleToNewRegisteredUser(newRegisteredUser);
        addPointsToNewRegisteredUser(newRegisteredUser);
        addNotificationForNewRegisteredUser(newRegisteredUser);
        userRepository.update(newRegisteredUser);
        return newRegisteredUser;
    }

    @Override
    public List<User> getAllPotentialJury() {
        return userRepository.getAllPotentialJury();
    }


    private void addRoleToNewRegisteredUser(User user) {
        Role role = userRepository.getRoleByName(UserRoles.USER.toString());

        Set<Role> roles = new HashSet<>();
        roles.add(role);
        user.setRoles(roles);
    }

    private void addPointsToNewRegisteredUser(User user) {

        Points points = new Points();
        Points pointsToAdd = pointsRepository.createPoints(points);
        Set<Points> startingPoints = new HashSet<>();
        startingPoints.add(pointsToAdd);
        user.setPoints(startingPoints);
    }

    private void addNotificationForNewRegisteredUser(User user) {

        Set<Notification> userNotifications = new HashSet<>();
        Notification notification = sendMessageWhenUserCreated(user);
        Notification notificationToAdd = notificationRepository.create(notification);
        userNotifications.add(notificationToAdd);
        user.setNotifications(userNotifications);
    }

    @Override
    public User update(User user, User userToUpdate, Optional<MultipartFile> file, Optional<String> url) throws IOException {


        verifyUserHasRoles(user, UserRoles.USER, UserRoles.ORGANIZER);

        verifyIsUserOwnAccount(user.getId(), userToUpdate.getId(), IS_USER_OWN_ACCOUNT_ERROR_MESSAGE);

        if (file.isPresent() || url.isPresent()) {
            String profileImageUrl = imgurService.uploadImageToImgur(file, url);

            if (!profileImageUrl.isBlank()) {
                userToUpdate.setProfileImage(profileImageUrl);
            }
        }

        return userRepository.update(userToUpdate);
    }


}
