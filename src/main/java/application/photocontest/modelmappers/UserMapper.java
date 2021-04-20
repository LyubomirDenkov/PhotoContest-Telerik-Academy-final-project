package application.photocontest.modelmappers;

import application.photocontest.models.User;
import application.photocontest.models.UserCredentials;
import application.photocontest.models.dto.RegisterDto;
import application.photocontest.models.dto.UpdateUserDto;
import application.photocontest.repository.contracts.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import static application.photocontest.constants.Constants.OLD_PASSWORD_NOT_MATCH;
import static application.photocontest.constants.Constants.PASSWORDS_NOT_MATCH_ERROR_MESSAGE;

@Component
public class UserMapper {



    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserMapper(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }


    public User fromDto(RegisterDto registerDto) {

        if (!registerDto.getPassword().equals(registerDto.getRepeatPassword())) {
            throw new IllegalArgumentException(PASSWORDS_NOT_MATCH_ERROR_MESSAGE);
        }

        UserCredentials userCredentials = new UserCredentials();
        userCredentials.setUserName(registerDto.getUserName());
        userCredentials.setPassword(passwordEncoder.encode(registerDto.getPassword()));


        User user = new User();
        user.setFirstName(registerDto.getFirstName());
        user.setLastName(registerDto.getLastName());
        user.setUserCredentials(userCredentials);

        return user;
    }

    public User fromDto(int id, UpdateUserDto userDto) {

        User user = userRepository.getById(id);

        if (!passwordEncoder.matches(userDto.getOldPassword(), user.getUserCredentials().getPassword())) {
            throw new UnsupportedOperationException(OLD_PASSWORD_NOT_MATCH);
        }
        if (!userDto.getNewPassword().equals(userDto.getRepeatPassword())) {
            throw new IllegalArgumentException(PASSWORDS_NOT_MATCH_ERROR_MESSAGE);
        }

        user.getUserCredentials().setPassword(passwordEncoder.encode(userDto.getNewPassword()));
        return user;
    }

}
