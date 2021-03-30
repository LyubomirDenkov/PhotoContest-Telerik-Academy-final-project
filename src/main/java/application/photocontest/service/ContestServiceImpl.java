package application.photocontest.service;

import application.photocontest.exceptions.DuplicateEntityException;
import application.photocontest.exceptions.EntityNotFoundException;
import application.photocontest.exceptions.UnauthorizedOperationException;
import application.photocontest.models.Contest;
import application.photocontest.models.Organizer;
import application.photocontest.models.User;
import application.photocontest.models.UserCredentials;
import application.photocontest.repository.contracts.ContestRepository;
import application.photocontest.service.contracts.ContestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class ContestServiceImpl implements ContestService {

    private final ContestRepository contestRepository;

    @Autowired
    public ContestServiceImpl(ContestRepository contestRepository) {
        this.contestRepository = contestRepository;
    }

    @Override
    public List<Contest> getAll(UserCredentials userCredentials) {

        return contestRepository.getAll();
    }

    @Override
    public Contest getById(UserCredentials organizer, int id) {
        return contestRepository.getById(id);
    }

    @Override
    public Contest create(Organizer organizer, Contest contest) {

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
        addParticipantsToContest(contestToCreate);
        return contestToCreate;

    }

    @Override
    public Contest update(Organizer organizer, Contest contest) {


     if (!organizer.getCredentials().getUserName().equals(contest.getOrganizer().getCredentials().getUserName())) {
            throw new UnauthorizedOperationException("something");
        }

        Contest contestToUpdate = contestRepository.update(contest);
        addParticipantsToContest(contestToUpdate);
        return contestToUpdate;

    }



    @Override
    public void delete(Organizer organizer, int id) {
    }

    private void addParticipantsToContest(Contest contest) {
        Set<User> participants = contest.getParticipants();

        contest.setParticipants(participants);
        contestRepository.update(contest);
    }



}
