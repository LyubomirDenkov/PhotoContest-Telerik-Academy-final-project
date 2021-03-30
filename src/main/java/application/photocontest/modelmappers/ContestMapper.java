package application.photocontest.modelmappers;

import application.photocontest.models.*;
import application.photocontest.models.dto.ContestDto;
import application.photocontest.repository.contracts.CategoryRepository;
import application.photocontest.repository.contracts.ContestRepository;
import application.photocontest.repository.contracts.OrganizerRepository;
import application.photocontest.repository.contracts.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class ContestMapper {

    private final ContestRepository contestRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private final OrganizerRepository organizerRepository;

    @Autowired
    public ContestMapper(ContestRepository contestRepository, CategoryRepository categoryRepository, UserRepository userRepository, OrganizerRepository organizerRepository) {
        this.contestRepository = contestRepository;
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
        this.organizerRepository = organizerRepository;
    }

    public Contest fromDto(ContestDto contestDto, Organizer organizer) {

        Contest contest = new Contest();
        dtoToObject(contestDto, contest,organizer);

        return contest;
    }

    public Contest fromDto(int id, ContestDto contestDto) {
        Contest contestToUpdate = contestRepository.getById(id);

        contestToUpdate.setTitle(contestDto.getTitle());
        contestToUpdate.setCategory(categoryRepository.getById(contestDto.getCategoryId()));
        contestToUpdate.setStartingDate(contestDto.getStartingDate());
        contestToUpdate.setPhaseOne(contestDto.getPhaseOne());
        contestToUpdate.setPhaseTwo(contestDto.getPhaseTwo());
        contestToUpdate.setType(contestRepository.getByType(contestDto.getTypeId()));
        contestToUpdate.setPhase(contestRepository.getByPhase(contestDto.getPhaseId()));


        setContestJuryAndParticipants(contestDto,contestToUpdate);

        return contestToUpdate;
    }

    public Contest dtoToObject(ContestDto contestDto, Contest contest, Organizer organizer) {

        contest.setTitle(contestDto.getTitle());
        contest.setCategory(categoryRepository.getById(contestDto.getCategoryId()));
        contest.setStartingDate(contestDto.getStartingDate());
        contest.setOrganizer(organizer);
        contest.setPhaseOne(contestDto.getPhaseOne());
        contest.setPhaseTwo(contestDto.getPhaseTwo());
        contest.setType(contestRepository.getByType(contestDto.getTypeId()));
        contest.setPhase(contestRepository.getByPhase(contestDto.getPhaseId()));
        setContestJuryAndParticipants(contestDto,contest);


        return contest;
    }

    private void setContestJuryAndParticipants(ContestDto contestDto, Contest contest){

        Set<User> jury = new HashSet<>();

        for (Integer userId : contestDto.getJury()) {
            User userToAdd = userRepository.getById(userId);
            if (userToAdd.getPoints() > 150) {
                jury.add(userToAdd);
            }
        }

        Set<User> participants = new HashSet<>();

        for (Integer userId : contestDto.getParticipants()) {
            User userToAdd = userRepository.getById(userId);
            if (jury.contains(userToAdd)) {
                continue;
            }
            participants.add(userToAdd);
        }

        contest.setJury(jury);
        contest.setParticipants(participants);

    }


}
