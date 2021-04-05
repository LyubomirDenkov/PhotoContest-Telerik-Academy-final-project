package application.photocontest.service;

import application.photocontest.enums.UserRoles;
import application.photocontest.exceptions.DuplicateEntityException;
import application.photocontest.exceptions.EntityNotFoundException;
import application.photocontest.exceptions.UnauthorizedOperationException;
import application.photocontest.models.Organizer;
import application.photocontest.models.Role;
import application.photocontest.models.UserCredentials;
import application.photocontest.repository.contracts.OrganizerRepository;
import application.photocontest.repository.contracts.UserRepository;
import application.photocontest.service.contracts.OrganizerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static application.photocontest.service.authorization.AuthorizationHelper.verifyUserHasRoles;

@Service
public class OrganizerServiceImpl implements OrganizerService {


    public static final String UPDATE_PROFILE_ERROR_MESSAGE = "Organizer can update his profile only by himself.";
    private final OrganizerRepository organizerRepository;
    private final UserRepository userRepository;

    @Autowired
    public OrganizerServiceImpl(OrganizerRepository organizerRepository, UserRepository userRepository) {
        this.organizerRepository = organizerRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<Organizer> getAll(UserCredentials userCredentials) {

        verifyUserHasRoles(userCredentials, UserRoles.ORGANIZER);

        return organizerRepository.getAll();
    }

    @Override
    public Organizer getById(UserCredentials userCredentials, int id) {

        verifyUserHasRoles(userCredentials, UserRoles.ORGANIZER);

        return null;
    }

    @Override
    public Organizer getByUserName(String userName) {
        return organizerRepository.getByUserName(userName);
    }

    @Override
    public Organizer create(UserCredentials userCredentials, Organizer organizer) {

        verifyUserHasRoles(userCredentials, UserRoles.ORGANIZER);
        boolean isUserNameExist = true;

        try {
            organizerRepository.getByUserName(organizer.getCredentials().getUserName());
        } catch (EntityNotFoundException e) {
            isUserNameExist = false;
        }

        if (isUserNameExist) {
            throw new DuplicateEntityException("");
        }


       Organizer organizerToCreate = organizerRepository.create(organizer);
       addRoleToRegisteredOrganizer(organizer);
       return organizerToCreate;
    }

    @Override
    public Organizer update(UserCredentials userCredentials, Organizer organizer) {
        verifyUserHasRoles(userCredentials, UserRoles.ORGANIZER);

        if (!userCredentials.getUserName().equalsIgnoreCase(organizer.getCredentials().getUserName())){
            throw new UnauthorizedOperationException(UPDATE_PROFILE_ERROR_MESSAGE);
        }
        return organizerRepository.update(organizer);

    }

    @Override
    public void delete(UserCredentials userCredentials, int id) {

        verifyUserHasRoles(userCredentials, UserRoles.ORGANIZER);
        organizerRepository.delete(id);

    }

    public void addRoleToRegisteredOrganizer(Organizer organizer) {
        Role role = userRepository.getRoleByName(UserRoles.ORGANIZER.toString());
        Set<Role> roles;
        if (organizer.getCredentials().getRoles() == null) {
            roles = new HashSet<>();
        } else {
            roles = organizer.getCredentials().getRoles();
        }
        roles.add(role);
        organizer.getCredentials().setRoles(roles);
        organizerRepository.update(organizer);
    }
}
