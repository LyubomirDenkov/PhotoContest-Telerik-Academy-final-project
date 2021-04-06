package application.photocontest.service;

import application.photocontest.enums.UserRoles;
import application.photocontest.exceptions.DuplicateEntityException;
import application.photocontest.exceptions.EntityNotFoundException;
import application.photocontest.exceptions.UnauthorizedOperationException;
import application.photocontest.models.*;
import application.photocontest.repository.contracts.UserRepository;
import application.photocontest.service.contracts.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

import static application.photocontest.service.authorization.AuthorizationHelper.verifyIsUserOwnAccount;
import static application.photocontest.service.authorization.AuthorizationHelper.*;

@Service
public class UserServiceImpl implements UserService {


    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> getAll(User user) {
        return userRepository.getAll();
    }

    @Override
    public User getById(User user, int id) {

        verifyUserHasRoles(user, UserRoles.USER, UserRoles.ORGANIZER);

       if (!user.isOrganizer()){
           verifyIsUserOwnAccount(user.getId(),id,"something");
       }

        return userRepository.getById(id);
    }

    @Override
    public User getUserByUserName(String userName) {
        return userRepository.getUserByUserName(userName);
    }


    @Override
    public User create(User user) {

        boolean isUserNameExist = true;

        try {
            userRepository.getUserByUserName(user.getUserCredentials().getUserName());
        } catch (EntityNotFoundException e) {
            isUserNameExist = false;
        }

        if (isUserNameExist) {
            throw new DuplicateEntityException("");
        }

        User newRegisteredUser = userRepository.create(user);

        addRoleToRegisteredUser(newRegisteredUser);

        return newRegisteredUser;

    }

    public void addRoleToRegisteredUser(User user) {
        Role role = userRepository.getRoleByName(UserRoles.USER.toString());
        Set<Role> roles = user.getRoles();
        roles.add(role);
        user.setRoles(roles);
        userRepository.update(user);
    }


    @Override
    public User update(User user, User userToUpdate) {

        boolean isUserNameExist = true;

        verifyUserHasRoles(user, UserRoles.USER,UserRoles.ORGANIZER);

        verifyIsUserOwnAccount(user.getId(), userToUpdate.getId(), "something");

        try {
            userRepository.getUserByUserName(userToUpdate.getUserCredentials().getUserName());
        } catch (EntityNotFoundException e) {
            isUserNameExist = false;
        }

        if (isUserNameExist) {
            throw new DuplicateEntityException("User", "username", userToUpdate.getUserCredentials().getUserName());
        }

        return userRepository.update(userToUpdate);
    }

    @Override
    public void delete(User user, int id) {


        verifyUserHasRoles(user, UserRoles.USER,UserRoles.ORGANIZER);

        verifyIsUserOwnAccount(user.getId(), id, "something");

        userRepository.delete(id);
    }

}
