package application.photocontest.modelmappers;

import application.photocontest.models.Rank;
import application.photocontest.models.User;
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

        User user = new User();
        user.setUserName(registerDto.getUserName());
        user.setEmail(registerDto.getEmail());
        user.setFirstName(registerDto.getFirstName());
        user.setLastName(registerDto.getLastName());
        user.setPassword(registerDto.getPassword());
        user.setRank(baseRank);

        return user;
    }

    public User fromDto(int id, UpdateUserDto userDto) {

        User user = userRepository.getById(id);
        user.setEmail(userDto.getEmail());

        if (userDto.getOldPassword().isPresent() && userDto.getNewPassword().isPresent() && userDto.getRepeatPassword().isPresent()){

            comparePasswords(userDto.getOldPassword().toString(),user.getPassword());

            comparePasswords(userDto.getNewPassword().toString(),userDto.getRepeatPassword().toString());

            user.setPassword(userDto.getNewPassword().toString());
        }

        return user;
    }

    private void comparePasswords(String firstPassword,String secondPassword){
        if (!firstPassword.equals(secondPassword)){
            throw new IllegalArgumentException();
        }
    }

    /*private void dtoToObject(UserDto userDto, User user) {

        City city = cityService.getById(userDto.getAddress().getCityId());

        Address address = new Address();

        address.setStreetName(userDto.getAddress().getStreetName());

        address.setCity(city);

        user.setFirstName(userDto.getFirstName());

        user.setLastName(userDto.getLastName());

        user.setEmail(userDto.getEmail());

        user.setAddress(address);

    }*/
}
