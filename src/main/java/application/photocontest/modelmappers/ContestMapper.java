package application.photocontest.modelmappers;

import application.photocontest.models.Contest;
import application.photocontest.models.User;
import application.photocontest.models.dto.ContestDto;
import application.photocontest.repository.contracts.CategoryRepository;
import application.photocontest.repository.contracts.ContestRepository;
import application.photocontest.repository.contracts.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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

    public Contest fromDto(ContestDto contestDto) {

        Contest contest = new Contest();
        dtoToObject(contestDto, contest);

        return contest;
    }

    public Contest dtoToObject(ContestDto contestDto, Contest contest) {

        contest.setCategory(categoryRepository.getById(contestDto.getCategoryId()));
        contest.setPhaseOne(contestDto.getPhaseOne());
        contest.setPhaseTwo(contestDto.getPhaseTwo());
        contest.setTitle(contestDto.getTitle());
        contest.setCreator(userRepository.getById(contestDto.getCreatorId()));

       Set<User> participants = new HashSet<>();

        for (Integer participant : contestDto.getParticipants()) {
            participants.add(userRepository.getById(participant));
        }

        Set<User> jury = new HashSet<>();

        for (Integer judge : contestDto.getJury()) {
            jury.add(userRepository.getById(judge));
        }

        contest.setJury(jury);
        contest.setParticipants(participants);


        return contest;
    }

}
