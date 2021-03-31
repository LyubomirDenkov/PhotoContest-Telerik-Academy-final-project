package application.photocontest.service;

import application.photocontest.enums.UserRoles;
import application.photocontest.exceptions.DuplicateEntityException;
import application.photocontest.exceptions.EntityNotFoundException;
import application.photocontest.exceptions.UnauthorizedOperationException;
import application.photocontest.models.Contest;
import application.photocontest.models.Organizer;
import application.photocontest.models.User;
import application.photocontest.models.UserCredentials;
import application.photocontest.repository.contracts.ContestRepository;
import application.photocontest.repository.contracts.UserRepository;
import application.photocontest.service.contracts.ContestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Set;

import static application.photocontest.service.authorization.AuthorizationHelper.verifyUserHasRoles;

@Service
public class ContestServiceImpl implements ContestService {

    public static final String USER_IS_ALREADY_IN_THIS_CONTEST = "User is already in this contest.";
    public static final String USER_CANNOT_ADD_OTHER_USERS_IN_CONTESTS = "User cannot add other users in contests.";
    private final ContestRepository contestRepository;
    private final UserRepository userRepository;
    @Autowired
    public ContestServiceImpl(ContestRepository contestRepository, UserRepository userRepository) {
        this.contestRepository = contestRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<Contest> getAll(UserCredentials userCredentials) {

        List<Contest> contests = contestRepository.getAll();


        for (int i = 0; i < contests.size(); i++) {

            if(contests.get(i).getPhase().getName().equals("finished")){
                continue;
            }

            LocalDateTime dateNow = LocalDateTime.now();
            LocalDateTime contestStartingDate = contests.get(i).getStartingDate();
            int phaseOneDays = contests.get(i).getPhaseOne();
            int phaseTwoHours = contests.get(i).getPhaseTwo();

            if (dateNow.isBefore(contestStartingDate)){
                continue;
            }

            if (dateNow.isAfter(contestStartingDate) &&
                    dateNow.plusDays(phaseOneDays).isBefore(contestStartingDate.plusDays(phaseOneDays).plusHours(phaseTwoHours))){
                contests.get(i).setPhase(contestRepository.getByPhase(2));
                continue;
            }

            dateNow = dateNow.plusDays(phaseOneDays).plusHours(phaseTwoHours);
            contestStartingDate = contestStartingDate.plusDays(phaseOneDays).plusHours(phaseTwoHours);

            if (dateNow.isAfter(contestStartingDate)){
                contests.get(i).setPhase(contestRepository.getByPhase(3));
            }


            System.out.println();

        }


        return contests;
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
        addParticipantsToContestAndPoints(contestToCreate);
        return contestToCreate;

    }

    @Override
    public Contest update(Organizer organizer, Contest contest) {


     if (!organizer.getCredentials().getUserName().equals(contest.getOrganizer().getCredentials().getUserName())) {
            throw new UnauthorizedOperationException("something");
        }

        Contest contestToUpdate = contestRepository.update(contest);

        Set<User> participants = contest.getParticipants();
        contest.setParticipants(participants);
        contestRepository.update(contest);
        return contestToUpdate;

    }



    @Override
    public void delete(Organizer organizer, int id) {
    }

    @Override
    public void addUserToContest(UserCredentials userCredentials, int contestId, int userId) {
        verifyUserHasRoles(userCredentials, UserRoles.USER);

                Contest contest = contestRepository.getById(contestId);
                User user = userRepository.getById(userId);

                if (!user.getCredentials().getUserName().equals(userCredentials.getUserName())){
                    throw new UnauthorizedOperationException(USER_CANNOT_ADD_OTHER_USERS_IN_CONTESTS);
                }
                if (contest.getType().getName().equalsIgnoreCase("open")) {
                    if (!contest.getPhase().getName().equalsIgnoreCase("phase |")){
                        throw new UnauthorizedOperationException("You cannot join in a contest which is not in phase |.");
                    }
                } else {
                    throw new UnauthorizedOperationException("You cannot join in an invitational contest.");
                }

        Set<User> participants = contest.getParticipants();
        if (participants.contains(user)) {
            throw new DuplicateEntityException(USER_IS_ALREADY_IN_THIS_CONTEST);
        }



        participants.add(user);
        contest.setParticipants(participants);
        user.setPoints(user.getPoints() + 1);
        userRepository.update(user);
        contestRepository.update(contest);

    }


    private void addParticipantsToContestAndPoints(Contest contest) {
        Set<User> participants = contest.getParticipants();
        for (User participant : participants) {
            participant.setPoints(participant.getPoints() + 3);
            userRepository.update(participant);
        }
        contest.setParticipants(participants);
        contestRepository.update(contest);
    }

}
