package application.photocontest.service;

import application.photocontest.enums.UserRoles;
import application.photocontest.models.Organizer;
import application.photocontest.models.UserCredentials;
import application.photocontest.repository.contracts.OrganizeRepository;
import application.photocontest.service.contracts.OrganizeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static application.photocontest.service.authorization.AuthorizationHelper.verifyUserHasRoles;

@Service
public class OrganizerServiceImpl implements OrganizeService {


    private final OrganizeRepository organizeRepository;

    @Autowired
    public OrganizerServiceImpl(OrganizeRepository organizeRepository) {
        this.organizeRepository = organizeRepository;
    }

    @Override
    public List<Organizer> getAll(UserCredentials userCredentials) {

        verifyUserHasRoles(userCredentials, UserRoles.ORGANIZER);

        return organizeRepository.getAll();
    }

    @Override
    public Organizer getById(UserCredentials userCredentials, int id) {

        verifyUserHasRoles(userCredentials, UserRoles.ORGANIZER);

        return null;
    }

    @Override
    public Organizer getByUserName(String userName) {
        return organizeRepository.getByUserName(userName);
    }

    @Override
    public Organizer create(UserCredentials userCredentials, Organizer type) {

        verifyUserHasRoles(userCredentials, UserRoles.ORGANIZER);

        return null;
    }

    @Override
    public Organizer update(UserCredentials userCredentials, Organizer type) {

        verifyUserHasRoles(userCredentials, UserRoles.ORGANIZER);
        return null;
    }

    @Override
    public void delete(UserCredentials userCredentials, int id) {

        verifyUserHasRoles(userCredentials, UserRoles.ORGANIZER);

    }
}
