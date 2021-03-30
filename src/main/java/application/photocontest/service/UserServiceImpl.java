package application.photocontest.service;

import application.photocontest.enums.UserRoles;
import application.photocontest.exceptions.DuplicateEntityException;
import application.photocontest.exceptions.EntityNotFoundException;
import application.photocontest.models.*;
import application.photocontest.repository.contracts.UserRepository;
import application.photocontest.service.contracts.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static application.photocontest.enums.UserRanks.*;
import static application.photocontest.service.authorization.AuthorizationHelper.verifyIsUserOwnAccount;
import static application.photocontest.service.authorization.AuthorizationHelper.*;

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


    @Override
    public UserCredentials getByUserName(String userName) {
        return userRepository.getByUserName(userName);
    }

    @Override
    public User getUserByUserName(String userName) {
        return userRepository.getUserByUserName(userName);
    }

    @Override
    public User getByEmail(String email) {
        User user = userRepository.getByEmail(email);
     //   calculateUserRank(user);
        return user;
    }

//  private void calculateUserRank(User user) {

//      verifyUserIsNotOrganizer(user);

//      if (user.getRanks().contains(userRepository.getRankByName(DICTATOR.toString()))) {
//          return;
//      }

//      if (user.getRanks().contains(userRepository.getRankByName(JUNKIE.toString()))) {
//          if (isUserHavePointsToUpgradeRank(user, JUNKIE_CEILING_POINTS)) {
//              setNewUserRank(user, ENTHUSIAST.toString());
//          }
//      }
//      if (user.getRanks().contains(userRepository.getRankByName(ENTHUSIAST.toString()))) {
//          if (isUserHavePointsToUpgradeRank(user, ENTHUSIAST_CEILING_POINTS)) {
//              setNewUserRank(user, MASTER.toString());
//          }
//      }
//      if (user.getRanks().contains(userRepository.getRankByName(MASTER.toString()))) {
//          if (isUserHavePointsToUpgradeRank(user, MASTER_CEILING_POINTS)) {
//              setNewUserRank(user, DICTATOR.toString());
//          }
//      }
//  }

//  private boolean isUserHavePointsToUpgradeRank(User user, int points) {
//      return user.getPoints() > points;
//  }

//  private void setNewUserRank(User user, String rankName) {
//      Rank newRank = userRepository.getRankByName(rankName);
//      Set<Rank> rankSet = user.getRanks();
//      rankSet.clear();
//      rankSet.add(newRank);
//      user.setRanks(rankSet);
//      userRepository.update(user);
//  }


    @Override
    public User create(User user) {

        User newRegisteredUser = userRepository.create(user);

        addRoleToRegisteredUser(newRegisteredUser);

        return newRegisteredUser;

    }

    public void addRoleToRegisteredUser(User user) {
        Role role = userRepository.getRoleByName(UserRoles.USER.toString());
        Set<Role> roles = user.getCredentials().getRoles();
        roles.add(role);
        user.getCredentials().setRoles(roles);
        userRepository.update(user);
    }

    public void addRoleToUser(User user) {
        Role role = userRepository.getRoleByName(UserRoles.USER.toString());
        Set<Role> roles = user.getCredentials().getRoles();
        roles.add(role);
        user.getCredentials().setRoles(roles);
        userRepository.update(user);
    }

    @Override
    public User update(UserCredentials userCredentials, User userToUpdate) {

        boolean isEmailExist = true;

        verifyUserHasRoles(userCredentials,UserRoles.USER);
        verifyIsUserOwnAccount(userCredentials,userToUpdate,"something");

        try {
            userRepository.getByUserName(userToUpdate.getCredentials().getUserName());
        }catch (EntityNotFoundException e){
            isEmailExist = false;
        }

        if (isEmailExist){
            throw new DuplicateEntityException("User","username",userToUpdate.getCredentials().getUserName());
        }

        return userRepository.update(userToUpdate);
    }

    @Override
    public void delete(UserCredentials userCredentials, int id) {

        User user = userRepository.getById(id);

        verifyUserHasRoles(userCredentials,UserRoles.USER);

        verifyIsUserOwnAccount(userCredentials,user,"something");

        userRepository.delete(id);
    }
}
