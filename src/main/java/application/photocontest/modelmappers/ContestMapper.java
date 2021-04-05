package application.photocontest.modelmappers;

import application.photocontest.models.*;
import application.photocontest.models.dto.ContestDto;
import application.photocontest.repository.contracts.CategoryRepository;
import application.photocontest.repository.contracts.ContestRepository;
import application.photocontest.repository.contracts.OrganizerRepository;
import application.photocontest.repository.contracts.UserRepository;
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
        dtoToObject(contestDto, contest, organizer);

        return contest;
    }

    public Contest fromDto(int id, ContestDto contestDto) {
        Contest contestToUpdate = contestRepository.getById(id);

        contestToUpdate.setTitle(contestDto.getTitle());
        contestToUpdate.setCategory(categoryRepository.getById(contestDto.getCategoryId()));
        contestToUpdate.setPhaseOne(contestDto.getPhaseOne());
        contestToUpdate.setPhaseTwo(contestDto.getPhaseTwo());
        contestToUpdate.setType(contestRepository.getTypeById(contestDto.getTypeId()));
        contestToUpdate.setBackgroundImage(contestDto.getBackgroundImage());



        return contestToUpdate;

    }



    public Contest dtoToObject(ContestDto contestDto, Contest contest, Organizer organizer) {

        contest.setTitle(contestDto.getTitle());
        contest.setCategory(categoryRepository.getById(contestDto.getCategoryId()));
        contest.setStartingDate(LocalDateTime.now());
        contest.setOrganizer(organizer);
        contest.setPhaseOne(contestDto.getPhaseOne());
        contest.setPhaseTwo(contestDto.getPhaseTwo());
        contest.setType(contestRepository.getTypeById(contestDto.getTypeId()));
        contest.setPointsAwarded(false);
        contest.setBackgroundImage(contestDto.getBackgroundImage());



        Set<Organizer> organizersJury = new HashSet<>();
        organizersJury.addAll(organizerRepository.getAll());
        contest.setOrganizersJury(organizersJury);




        return contest;
    }


}
