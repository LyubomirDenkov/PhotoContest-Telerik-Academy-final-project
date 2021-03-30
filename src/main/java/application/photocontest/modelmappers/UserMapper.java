package application.photocontest.modelmappers;

import application.photocontest.models.Rank;
import application.photocontest.models.User;
import application.photocontest.models.UserCredentials;
import application.photocontest.models.dto.RegisterDto;
import application.photocontest.models.dto.UpdateUserDto;
import application.photocontest.repository.contracts.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static application.photocontest.enums.UserRanks.JUNKIE;

@Component
public class UserMapper {


    private final UserRepository userRepository;

    @Autowired
    public UserMapper(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public User fromDto(RegisterDto registerDto) {

        Rank baseRank = userRepository.getRankByName(JUNKIE.toString());

        UserCredentials userCredentials = new UserCredentials();
        userCredentials.setUserName(registerDto.getUserName());
        userCredentials.setPassword(registerDto.getPassword());

        User user = new User();
        user.setFirstName(registerDto.getFirstName());
        user.setLastName(registerDto.getLastName());
        user.setCredentials(userCredentials);

        user.setRank(baseRank);

        return user;
    }

    public User fromDto(int id, UpdateUserDto userDto) {


        User user = userRepository.getById(id);

        if (!userDto.getOldPassword().equals(user.getCredentials().getPassword())){
            throw new IllegalArgumentException("Old password not match");
        }
        if (!userDto.getNewPassword().equals(userDto.getRepeatPassword())){
            throw new IllegalArgumentException("passwords not match");
        }

        user.getCredentials().setPassword(userDto.getNewPassword());
        return user;
    }

}
