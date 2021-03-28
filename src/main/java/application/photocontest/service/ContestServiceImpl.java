package application.photocontest.service;

import application.photocontest.exceptions.EntityNotFoundException;
import application.photocontest.models.Contest;
import application.photocontest.models.User;
import application.photocontest.repository.contracts.ContestRepository;
import application.photocontest.service.contracts.ContestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static application.photocontest.service.authorization.AuthorizationHelper.verifyUserIsCustomerOrEmployee;

@Service
public class ContestServiceImpl implements ContestService {

    private final ContestRepository contestRepository;

    @Autowired
    public ContestServiceImpl(ContestRepository contestRepository) {
        this.contestRepository = contestRepository;
    }

    @Override
    public List<Contest> getAll(User user) {
        verifyUserIsCustomerOrEmployee(user);
        return contestRepository.getAll();
    }

    @Override
    public Contest getById(User user, int id) {

        verifyUserIsCustomerOrEmployee(user);

        try {
            return contestRepository.getById(id);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @Override
    public Contest create(Contest name) {
        return null;
    }

    @Override
    public Contest update(Contest name,int id) {
        return null;
    }

    @Override
    public void delete(Contest contest, int id) {

    }
}
