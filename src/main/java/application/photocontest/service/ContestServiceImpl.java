package application.photocontest.service;

import application.photocontest.exceptions.DuplicateEntityException;
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
import java.util.Set;

import static application.photocontest.service.authorization.AuthorizationHelper.*;

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
    public Contest create(User user, Contest contest) {
        verifyUserIsOrganizer(user);
        boolean ifContestTitleExist = true;

        try {
            contestRepository.getByTitle(contest.getTitle());
        } catch (EntityNotFoundException e) {
            ifContestTitleExist = false;
        }
        if (ifContestTitleExist) {
            throw new DuplicateEntityException("Contest", "title", contest.getTitle());
        }

        Contest contestToCreate = contestRepository.create(contest);
        addJuryAndParticipantsToContest(contestToCreate);
        return contestToCreate;

    }

    @Override
    public Contest update(Contest user, Contest name) {
        return null;
    }

    @Override
    public void delete(Contest contest, int id) {

    }

    private void addJuryAndParticipantsToContest(Contest contest) {
        Set<User> jury = contest.getJury();
        Set<User> participants = contest.getParticipants();

        contest.setParticipants(participants);
        contest.setJury(jury);
        contestRepository.update(contest);
    }
}
