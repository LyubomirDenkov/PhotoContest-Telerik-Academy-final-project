package application.photocontest.service.contracts;

import application.photocontest.models.Contest;
import application.photocontest.models.User;
import application.photocontest.service.contracts.genericservice.GetServiceOperations;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface UserService extends GetServiceOperations<User> {

    User getUserByUserName(String userName);

    List<User> getLeaderboard(User user);

    User update(User user,User userToUpdate, Optional<MultipartFile> file, Optional<String> url) throws IOException;


    User create(User user, Optional<MultipartFile> file, Optional<String> url) throws IOException;

    List<User> getAllPotentialJury();

    List<User> getAllUsers();

    List<Contest> getUserContests(User user,int userId);
}
