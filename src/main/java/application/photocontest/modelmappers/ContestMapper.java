package application.photocontest.modelmappers;

import application.photocontest.models.*;
import application.photocontest.models.dto.ContestDto;
import application.photocontest.repository.contracts.CategoryRepository;
import application.photocontest.repository.contracts.ContestRepository;
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

    @Autowired
    public ContestMapper(ContestRepository contestRepository, CategoryRepository categoryRepository, UserRepository userRepository) {
        this.contestRepository = contestRepository;
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
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
        contestToUpdate.setType(contestRepository.getTypeById(contestDto.getTypeId()));
        contestToUpdate.setBackgroundImage(contestDto.getBackgroundImage());
        contestToUpdate.setPhase(contestRepository.getPhaseByName("ongoing"));



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
        contest.setType(contestRepository.getTypeById(contestDto.getTypeId()));
        contest.setBackgroundImage(contestDto.getBackgroundImage());
        contest.setPhase(contestRepository.getPhaseByName("ongoing"));

        Set<Image> images = new HashSet<>();
        contest.setImages(images);

        return contest;
    }


}
