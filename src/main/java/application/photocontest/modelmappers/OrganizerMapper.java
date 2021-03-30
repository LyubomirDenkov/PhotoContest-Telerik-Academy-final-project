package application.photocontest.modelmappers;

import application.photocontest.models.Organizer;
import application.photocontest.models.Rank;
import application.photocontest.models.User;
import application.photocontest.models.UserCredentials;
import application.photocontest.models.dto.RegisterDto;
import application.photocontest.models.dto.UpdateUserDto;
import application.photocontest.repository.contracts.OrganizeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static application.photocontest.enums.UserRanks.JUNKIE;

@Component
public class OrganizerMapper {


    private final OrganizeRepository organizeRepository;

    @Autowired
    public OrganizerMapper(OrganizeRepository organizeRepository) {
        this.organizeRepository = organizeRepository;
    }

    public Organizer fromDto(RegisterDto registerDto) {

        UserCredentials userCredentials = new UserCredentials();
        userCredentials.setUserName(registerDto.getUserName());
        userCredentials.setEmail(registerDto.getEmail());
        userCredentials.setPassword(registerDto.getPassword());

        Organizer organizer = new Organizer();
        organizer.setFirstName(registerDto.getFirstName());
        organizer.setLastName(registerDto.getLastName());
        organizer.setCredentials(userCredentials);


        return organizer;
    }

    public Organizer fromDto(int id, UpdateUserDto userDto) {


        Organizer organizer = organizeRepository.getById(id);

        if (!organizer.getCredentials().getPassword().equals(userDto.getPassword())) {
            throw new IllegalArgumentException("something");
        }

        organizer.getCredentials().setEmail(userDto.getEmail());
        return organizer;
    }
}
