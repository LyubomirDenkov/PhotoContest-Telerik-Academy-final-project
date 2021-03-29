package application.photocontest.modelmappers;

import application.photocontest.models.Contest;
import application.photocontest.models.User;
import application.photocontest.models.UserCredentials;
import application.photocontest.models.dto.ContestDto;
import application.photocontest.repository.contracts.CategoryRepository;
import application.photocontest.repository.contracts.ContestRepository;
import application.photocontest.repository.contracts.OrganizeRepository;
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
    private final OrganizeRepository organizeRepository;

    @Autowired
    public ContestMapper(ContestRepository contestRepository, CategoryRepository categoryRepository, UserRepository userRepository, OrganizeRepository organizeRepository) {
        this.contestRepository = contestRepository;
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
        this.organizeRepository = organizeRepository;
    }

    public Contest fromDto(ContestDto contestDto, UserCredentials userCredentials) {

        Contest contest = new Contest();
        dtoToObject(contestDto, contest, userCredentials);

        return contest;
    }

    public Contest fromDto(int id, ContestDto contestDto) {
        Contest contest = contestRepository.getById(id);

        contest.setTitle(contestDto.getTitle());
        contest.setCategory(categoryRepository.getById(contestDto.getCategoryId()));
        contest.setOrganizer(organizeRepository.getById(contestDto.getOrganizerId()));
        contest.setStartingDate(contestDto.getStarting_date());
        contest.setPhaseOne(contestDto.getPhaseOne());
        contest.setPhaseTwo(contestDto.getPhaseTwo());

        contest.setTitle(contestDto.getTitle());

        setContestJuryAndParticipants(contestDto,contest);

        return contest;
    }

    public Contest dtoToObject(ContestDto contestDto, Contest contest, UserCredentials userCredentials) {


        /*contest.setCategory(categoryRepository.getById(contestDto.getCategoryId()));
        contest.setPhaseOne(contestDto.getPhaseOne());
        contest.setPhaseTwo(contestDto.getPhaseTwo());
        contest.setTitle(contestDto.getTitle());
        contest.setCreator(user);
*/
        setContestJuryAndParticipants(contestDto,contest);


        return contest;
    }

    private void setContestJuryAndParticipants(ContestDto contestDto, Contest contest){

        Set<User> participants = new HashSet<>();

        /*for (Integer participant : contestDto.getParticipants()) {
            User user = userRepository.getById(participant);
            if (user.isOrganizer()) {
                continue;
            }
            participants.add(user);
        }
*/

        contest.setParticipants(participants);
    }

}
