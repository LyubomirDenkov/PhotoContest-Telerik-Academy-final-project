package application.photocontest.modelmappers;

import application.photocontest.enums.ContestPhases;
import application.photocontest.models.*;
import application.photocontest.models.dto.ContestDto;
import application.photocontest.repository.contracts.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Component
public class ContestMapper {

    private final ContestRepository contestRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private final TypeRepository typeRepository;
    private final PhaseRepository phaseRepository;

    @Autowired
    public ContestMapper(ContestRepository contestRepository, CategoryRepository categoryRepository, UserRepository userRepository, TypeRepository typeRepository, PhaseRepository phaseRepository) {
        this.contestRepository = contestRepository;
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
        this.typeRepository = typeRepository;
        this.phaseRepository = phaseRepository;
    }

    public Contest fromDto(ContestDto contestDto, User user) {

        Contest contest = new Contest();
        dtoToObject(contestDto, contest, user);

        return contest;
    }

    public Contest fromDto(int id, ContestDto contestDto) {
        Contest contestToUpdate = contestRepository.getById(id);
        LocalDateTime localDateTime = LocalDateTime.now();


        contestToUpdate.setTitle(contestDto.getTitle());
        contestToUpdate.setCategory(categoryRepository.getById(contestDto.getCategoryId()));
        contestToUpdate.setTimeTillVoting(java.sql.Timestamp.valueOf(localDateTime.plusDays(contestDto.getPhaseOne())));
        contestToUpdate.setTimeTillFinished(java.sql.Timestamp.valueOf(localDateTime.plusDays(contestDto.getPhaseOne()).
                plusHours(contestDto.getPhaseTwo())));
        contestToUpdate.setType(typeRepository.getById(contestDto.getTypeId()));
        contestToUpdate.setBackgroundImage(contestDto.getBackgroundImage());
        contestToUpdate.setPhase(phaseRepository.getPhaseByName(ContestPhases.ONGOING.toString()));



        return contestToUpdate;

    }



    public Contest dtoToObject(ContestDto contestDto, Contest contest, User user) {
        LocalDateTime localDateTime = LocalDateTime.now();

        contest.setTitle(contestDto.getTitle());
        contest.setCategory(categoryRepository.getById(contestDto.getCategoryId()));
        contest.setUser(user);
        contest.setTimeTillVoting(java.sql.Timestamp.valueOf(localDateTime.plusDays(contestDto.getPhaseOne())));
        contest.setTimeTillFinished(java.sql.Timestamp.valueOf(localDateTime.plusDays(contestDto.getPhaseOne()).
                plusHours(contestDto.getPhaseTwo())));
        contest.setType(typeRepository.getById(contestDto.getTypeId()));
        contest.setBackgroundImage(contestDto.getBackgroundImage());
        contest.setPhase(phaseRepository.getPhaseByName(ContestPhases.ONGOING.toString()));

        Set<Image> images = new HashSet<>();
        contest.setImages(images);

        return contest;
    }

    public ContestDto toDto(Contest contest) {
        int phaseOne = 0;
        int phaseTwo = 0;

        ContestDto contestDto = new ContestDto();
        contestDto.setTitle(contest.getTitle());
        contestDto.setCategoryId(contest.getCategory().getId());
        contestDto.setPhaseOne(phaseOne);
        contestDto.setPhaseTwo(phaseTwo);
        contestDto.setTypeId(contest.getId());
        contestDto.setBackgroundImage(contest.getBackgroundImage());
        Set<Integer> participantsFromContest = new HashSet<>();
        for (User participant : contest.getParticipants()) {
            participantsFromContest.add(participant.getId());
        }
        contestDto.setParticipants(participantsFromContest);

        Set<Integer> juryFromContest = new HashSet<>();
        for (User juror : contest.getJury()) {
            participantsFromContest.add(juror.getId());
        }
        contestDto.setJury(juryFromContest);

        contestDto.setImages(contest.getImages());
        return contestDto;
    }


}
