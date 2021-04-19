package application.photocontest.modelmappers;

import application.photocontest.models.User;
import application.photocontest.models.UserCredentials;
import application.photocontest.models.dto.RegisterDto;
import application.photocontest.models.dto.UpdateUserDto;
import application.photocontest.repository.contracts.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {


    private static final String PASSWORDS_NOT_MATCH_ERROR_MESSAGE = "Passwords not match";
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserMapper(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }


    public User fromDto(RegisterDto registerDto) {

        if (!registerDto.getPassword().equals(registerDto.getRepeatPassword())){
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

        if (!userDto.getOldPassword().equals(user.getUserCredentials().getPassword())){
            throw new UnsupportedOperationException("Old password not match");
        }
        if (!userDto.getNewPassword().equals(userDto.getRepeatPassword())){
            throw new IllegalArgumentException(PASSWORDS_NOT_MATCH_ERROR_MESSAGE);
        }

        user.getUserCredentials().setPassword(passwordEncoder.encode(userDto.getNewPassword()));
        return user;
    }

}
