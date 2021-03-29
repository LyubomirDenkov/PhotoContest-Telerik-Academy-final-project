package application.photocontest.modelmappers;

import application.photocontest.models.Contest;
import application.photocontest.models.User;
import application.photocontest.models.dto.ContestDto;
import application.photocontest.repository.contracts.CategoryRepository;
import application.photocontest.repository.contracts.ContestRepository;
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
        Contest contest = contestRepository.getById(id);

        contest.setTitle(contestDto.getTitle());
        contest.setCategory(categoryRepository.getById(contestDto.getCategoryId()));
        contest.setPhaseOne(contestDto.getPhaseOne());
        contest.setPhaseTwo(contestDto.getPhaseTwo());
        contest.setTitle(contestDto.getTitle());

        setContestJuryAndParticipants(contestDto,contest);

        return contest;
    }

    public Contest dtoToObject(ContestDto contestDto, Contest contest, User user) {


        contest.setCategory(categoryRepository.getById(contestDto.getCategoryId()));
        contest.setPhaseOne(contestDto.getPhaseOne());
        contest.setPhaseTwo(contestDto.getPhaseTwo());
        contest.setTitle(contestDto.getTitle());
        contest.setCreator(user);

        setContestJuryAndParticipants(contestDto,contest);


        return contest;
    }

    private void setContestJuryAndParticipants(ContestDto contestDto, Contest contest){

        Set<User> participants = new HashSet<>();

        for (Integer participant : contestDto.getParticipants()) {
            User user = userRepository.getById(participant);
            if (user.isOrganizer()) {
                continue;
            }
            participants.add(user);
        }


        contest.setParticipants(participants);
    }

}
