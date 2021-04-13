package application.photocontest.modelmappers;

import application.photocontest.models.Points;
import application.photocontest.models.User;
import application.photocontest.models.UserCredentials;
import application.photocontest.models.dto.RegisterDto;
import application.photocontest.models.dto.UpdateUserDto;
import application.photocontest.repository.contracts.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static application.photocontest.enums.UserRanks.JUNKIE;

@Component
public class UserMapper {


    private final UserRepository userRepository;

    @Autowired
    public UserMapper(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public User fromDto(RegisterDto registerDto) {

        UserCredentials userCredentials = new UserCredentials();
        userCredentials.setUserName(registerDto.getUserName());
        userCredentials.setPassword(registerDto.getPassword());

        User user = new User();
        user.setFirstName(registerDto.getFirstName());
        user.setLastName(registerDto.getLastName());
        user.setUserCredentials(userCredentials);

        return user;
    }

    public User fromDto(int id, UpdateUserDto userDto) {

        User user = userRepository.getById(id);

        if (!userDto.getOldPassword().equals(user.getUserCredentials().getPassword())){
            throw new IllegalArgumentException("Old password not match");
        }
        if (!userDto.getNewPassword().equals(userDto.getRepeatPassword())){
            throw new IllegalArgumentException("passwords not match");
        }

        user.getUserCredentials().setPassword(userDto.getNewPassword());
        return user;
    }

}
