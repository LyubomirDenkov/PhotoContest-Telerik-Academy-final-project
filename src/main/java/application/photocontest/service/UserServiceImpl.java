package application.photocontest.service;

import application.photocontest.models.User;
import application.photocontest.repository.contracts.UserRepository;
import application.photocontest.service.contracts.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static application.photocontest.enums.UserRanks.*;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> getAll() {
        return null;
    }

    @Override
    public User getById(int id) {

        User user = userRepository.getById(id);
        calculateUserRank(user);
        return user;


    }

    private void calculateUserRank(User user){

        //TODO optimized
        /*
        Ranking:
         (0-50) points – Junkie
         (51 – 150) points – Enthusiast
         (151 – 1000) points – Master (can now be invited as jury)
         (1001 – infinity) points – Wise and Benevolent Photo Dictator (can still be jury)
         */


        if (user.getRank().equals(JUNKIE.toString()) && user.getPoints() > 50){
                user.setRank(ENTHUSIAST.toString());
            return;
        }
        if (user.getRank().equals(ENTHUSIAST.toString()) && user.getPoints() > 150){
                user.setRank(MASTER.toString());
            return;
        }
        if (user.getRank().equals(MASTER.toString()) && user.getPoints() > 1000){
                user.setRank(DICTATOR.toString());
            return;
        }

    }


    @Override
    public User create(User name) {
        return null;
    }

    @Override
    public User update(User name) {
        return null;
    }

    @Override
    public void delete(int id) {

    }
}
