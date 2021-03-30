package application.photocontest.modelmappers;

import application.photocontest.models.Organizer;
import application.photocontest.models.UserCredentials;
import application.photocontest.models.dto.RegisterDto;
import application.photocontest.models.dto.UpdateUserDto;
import application.photocontest.repository.contracts.OrganizerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrganizerMapper {


    private final OrganizerRepository organizeRepository;

    @Autowired
    public OrganizerMapper(OrganizerRepository organizeRepository) {
        this.organizeRepository = organizeRepository;
    }


    public Organizer fromDto(RegisterDto registerDto) {

        UserCredentials userCredentials = new UserCredentials();
        userCredentials.setUserName(registerDto.getUserName());
        userCredentials.setPassword(registerDto.getPassword());

        Organizer organizer = new Organizer();
        organizer.setFirstName(registerDto.getFirstName());
        organizer.setLastName(registerDto.getLastName());
        organizer.setCredentials(userCredentials);


        return organizer;
    }

    public Organizer fromDto(int id, UpdateUserDto userDto) {

        Organizer organizer = organizeRepository.getById(id);

        if (!userDto.getOldPassword().equals(organizer.getCredentials().getPassword())){
            throw new IllegalArgumentException("Old password not match");
        }
        if (!userDto.getNewPassword().equals(userDto.getRepeatPassword())){
            throw new IllegalArgumentException("passwords not match");
        }

        organizer.getCredentials().setPassword(userDto.getNewPassword());

        return organizer;
    }
}
