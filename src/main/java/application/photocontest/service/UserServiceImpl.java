package application.photocontest.service;

import application.photocontest.enums.UserRoles;
import application.photocontest.exceptions.DuplicateEntityException;
import application.photocontest.exceptions.EntityNotFoundException;
import application.photocontest.models.*;
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


    private final UserRepository userRepository;
    private final ImgurService imgurService;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, ImgurService imgurService) {
        this.userRepository = userRepository;
        this.imgurService = imgurService;
    }

    @Override
    public List<User> getAll() {
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
    public User create(User user, Optional<MultipartFile> file, Optional<String> url) throws IOException {

        boolean isUserNameExist = true;
        boolean isImageUploaded = true;
        try {
            userRepository.getUserByUserName(user.getUserCredentials().getUserName());
        } catch (EntityNotFoundException e) {
            isUserNameExist = false;
        }

        if (isUserNameExist) {
            throw new DuplicateEntityException("USERNAME EXIST");
        }

        String profileImageUrl = "";
        try {
            profileImageUrl = imgurService.uploadImageToImgurAndReturnUrl(file, url);
        } catch (UnsupportedOperationException e) {
            isImageUploaded = false;
        }
        if (isImageUploaded) {
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

    @Override
    public List<User> getAllUsers() {
        return userRepository.getAllUsers();
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
    public User update(User user, User userToUpdate, Optional<MultipartFile> file, Optional<String> url) {

        boolean isImageUploaded = true;

        verifyUserHasRoles(user, UserRoles.USER, UserRoles.ORGANIZER);

        verifyIsUserOwnAccount(user.getId(), userToUpdate.getId(), "something");

        String profileImageUrl = "";
        try {
            profileImageUrl = imgurService.uploadImageToImgurAndReturnUrl(file, url);
        } catch (UnsupportedOperationException | IOException e) {
            isImageUploaded = false;
        }
        if (isImageUploaded) {
            user.setProfileImage(profileImageUrl);
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
