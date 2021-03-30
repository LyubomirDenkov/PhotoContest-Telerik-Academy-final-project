package application.photocontest.service;

import application.photocontest.enums.UserRoles;
import application.photocontest.models.Organizer;
import application.photocontest.models.UserCredentials;
import application.photocontest.repository.contracts.OrganizerRepository;
import application.photocontest.service.contracts.OrganizerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static application.photocontest.service.authorization.AuthorizationHelper.verifyUserHasRoles;

@Service
public class OrganizerServiceImpl implements OrganizerService {


    private final OrganizerRepository organizerRepository;

    @Autowired
    public OrganizerServiceImpl(OrganizerRepository organizerRepository) {
        this.organizerRepository = organizerRepository;
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

        return organizerRepository.create(organizer);
    }

    @Override
    public Organizer update(UserCredentials userCredentials, Organizer organizer) {

        verifyUserHasRoles(userCredentials, UserRoles.ORGANIZER);
        return null;
    }

    @Override
    public void delete(UserCredentials userCredentials, int id) {

        verifyUserHasRoles(userCredentials, UserRoles.ORGANIZER);

    }
}
