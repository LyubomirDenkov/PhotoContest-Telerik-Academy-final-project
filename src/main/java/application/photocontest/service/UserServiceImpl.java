package application.photocontest.service;

import application.photocontest.enums.UserRoles;
import application.photocontest.exceptions.DuplicateEntityException;
import application.photocontest.exceptions.EntityNotFoundException;
import application.photocontest.exceptions.IllegalDeleteException;
import application.photocontest.exceptions.UnauthorizedOperationException;
import application.photocontest.models.*;
import application.photocontest.repository.contracts.UserRepository;
import application.photocontest.service.contracts.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

import static application.photocontest.enums.UserRanks.*;
import static application.photocontest.service.authorization.AuthorizationHelper.verifyUserHasRoles;

@Service
public class UserServiceImpl implements UserService {

    private static final int JUNKIE_CEILING_POINTS = 50;
    private static final int ENTHUSIAST_CEILING_POINTS = 150;
    private static final int MASTER_CEILING_POINTS = 1000;
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> getAll(UserCredentials user) {
        return userRepository.getAll();
    }

    @Override
    public User getById(UserCredentials user, int id) {

        verifyUserHasRoles(user,UserRoles.ORGANIZER);

        return userRepository.getById(id);

    }

    //TODO later - calculate only after winning
    @Override
    public UserCredentials getByUserName(String userName) {
        UserCredentials user = userRepository.getByUserName(userName);
        //calculateUserRank(user);
        return user;
    }

    @Override
    public User getUserByUserName(String userName) {
        return null;
    }

    @Override
    public Organizer getOrganizerByUserName(String userName) {
        return null;
    }

    @Override
    public User getByEmail(String email) {
        User user = userRepository.getByEmail(email);
        calculateUserRank(user);
        return user;
    }

    //TODO optimize
    private void calculateUserRank(User user) {

        if (user.getRank().getName().equals(DICTATOR.toString())) {
            return;
        }

        if (user.getRank().getName().equals(JUNKIE.toString())) {
            if (isUserHavePointsToUpgradeRank(user, JUNKIE_CEILING_POINTS)) {
                setNewUserRank(user, ENTHUSIAST.toString());
            }
        }
        if (user.getRank().getName().equals(ENTHUSIAST.toString())) {
            if (isUserHavePointsToUpgradeRank(user, ENTHUSIAST_CEILING_POINTS)) {
                setNewUserRank(user, MASTER.toString());
            }
        }
        if (user.getRank().getName().equals(MASTER.toString())) {
            if (isUserHavePointsToUpgradeRank(user, MASTER_CEILING_POINTS)) {
                setNewUserRank(user, DICTATOR.toString());
            }
        }
    }

    private boolean isUserHavePointsToUpgradeRank(User user, int points) {
        return user.getPoints() > points;
    }

    private void setNewUserRank(User user, String rankName) {
        Rank newRank = userRepository.getRankByName(rankName);
        user.setRank(newRank);
        userRepository.update(user);
    }


    @Override
    public User create(User user) {

        User newRegisteredUser = userRepository.create(user);

        addRoleToRegisteredUser(newRegisteredUser);

        return newRegisteredUser;

    }

    public void addRoleToRegisteredUser(User user) {
        Role role = userRepository.getRoleByName(UserRoles.USER.toString());
        Set<Role> roles = user.getUserCredentials().getRoles();
        roles.add(role);
        user.getUserCredentials().setRoles(roles);
        userRepository.update(user);
    }

    public void addRoleToUser(User user) {
        Role role = userRepository.getRoleByName(UserRoles.USER.toString());
        Set<Role> roles = user.getUserCredentials().getRoles();
        roles.add(role);
        user.getUserCredentials().setRoles(roles);
        userRepository.update(user);
    }

    @Override
    public User update(UserCredentials userCredentials, User userToUpdate) {

        boolean isEmailExist = true;

        if (!userCredentials.getUserName().equals(userToUpdate.getUserCredentials().getUserName())) {
            throw new UnauthorizedOperationException("something");
        }

        try {
            userRepository.getByEmail(userToUpdate.getUserCredentials().getEmail());
        }catch (EntityNotFoundException e){
            isEmailExist = false;
        }

        if (isEmailExist){
            throw new DuplicateEntityException("User","email",userToUpdate.getUserCredentials().getEmail());
        }

        return userRepository.update(userToUpdate);
    }

    @Override
    public void delete(UserCredentials userCredentials, int id) {

        User user = userRepository.getById(id);

        if (!userCredentials.getUserName().equals(user.getUserCredentials().getUserName())) {
            throw new IllegalDeleteException("something");
        }
        userRepository.delete(id);
    }
}
