package application.photocontest.service;

import application.photocontest.enums.UserRoles;
import application.photocontest.exceptions.DuplicateEntityException;
import application.photocontest.exceptions.EntityNotFoundException;
import application.photocontest.models.*;
import application.photocontest.repository.contracts.ContestRepository;
import application.photocontest.repository.contracts.UserRepository;
import application.photocontest.service.contracts.ImgurService;
import application.photocontest.service.contracts.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

import static application.photocontest.service.authorization.AuthorizationHelper.verifyIsUserOwnAccount;
import static application.photocontest.service.authorization.AuthorizationHelper.*;

@Service
public class UserServiceImpl implements UserService {


    private static final String INITIAL_PROFILE_IMAGE = "https://i.imgur.com/GdDsxXO.png";

    private final UserRepository userRepository;
    private final ImgurService imgurService;
    private final ContestRepository contestRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, ImgurService imgurService, ContestRepository contestRepository) {
        this.userRepository = userRepository;
        this.imgurService = imgurService;
        this.contestRepository = contestRepository;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.getAllUsers();
    }

    @Override
    public List<Contest> getUserContests(User user, int userId) {

        verifyUserHasRoles(user,UserRoles.USER,UserRoles.ORGANIZER);

        if (!user.isOrganizer()){
            verifyIsUserOwnAccount(user.getId(),userId,"Something");
        }

        return contestRepository.getUserContests(user.getId());
    }

    @Override
    public List<User> getAll(User user) {
        verifyUserHasRoles(user,UserRoles.ORGANIZER);
        return userRepository.getAll();
    }

    @Override
    public User getById(User user, int id) {

        verifyUserHasRoles(user, UserRoles.USER, UserRoles.ORGANIZER);

        if (!user.isOrganizer()) {
            verifyIsUserOwnAccount(user.getId(), id, "something");
        }

        return userRepository.getById(id);
    }

    @Override
    public User getUserByUserName(String userName) {
        return userRepository.getUserByUserName(userName);
    }

    @Override
    public List<User> getLeaderboard(User user) {

        verifyUserHasRoles(user,UserRoles.USER,UserRoles.ORGANIZER);

        List<User> leaderboard = userRepository.getLeaderboard();

        if (leaderboard.size() > 20 ){
            return leaderboard.subList(0,20);
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
            throw new DuplicateEntityException("USERNAME EXIST");
        }

        String profileImageUrl = imgurService.uploadImageToImgurAndReturnUrl(file, url);

        if (profileImageUrl.isBlank()){
            user.setProfileImage(INITIAL_PROFILE_IMAGE);
        }else {
            user.setProfileImage(profileImageUrl);
        }

        User newRegisteredUser = userRepository.create(user);
        addRoleAndPointsToRegisteredUser(newRegisteredUser);

        return newRegisteredUser;
    }

    @Override
    public List<User> getAllPotentialJury() {
        return userRepository.getAllPotentialJury();
    }

    public void addRoleAndPointsToRegisteredUser(User user) {
        Role role = userRepository.getRoleByName(UserRoles.USER.toString());
        Set<Role> roles = user.getRoles();
        roles.add(role);
        user.setRoles(roles);
        Points points = new Points();
        Set<Points> startingPoints = new HashSet<>();
        startingPoints.add(points);
        userRepository.createPoints(points);
        user.setPoints(startingPoints);
        userRepository.update(user);
    }


    @Override
    public User update(User user, User userToUpdate, Optional<MultipartFile> file, Optional<String> url) throws IOException {


        verifyUserHasRoles(user, UserRoles.USER, UserRoles.ORGANIZER);

        verifyIsUserOwnAccount(user.getId(), userToUpdate.getId(), "something");

        if (file.isPresent() || url.isPresent()) {
            String profileImageUrl = imgurService.uploadImageToImgurAndReturnUrl(file, url);

            if (!profileImageUrl.isBlank()) {
                userToUpdate.setProfileImage(profileImageUrl);
            }
        }

        return userRepository.update(userToUpdate);
    }

    @Override
    public void delete(User user, int id) {


        verifyUserHasRoles(user, UserRoles.USER, UserRoles.ORGANIZER);

        verifyIsUserOwnAccount(user.getId(), id, "something");



        userRepository.delete(id);
    }

}
