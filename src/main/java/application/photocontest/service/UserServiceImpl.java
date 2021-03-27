package application.photocontest.service;

import application.photocontest.enums.UserRanks;
import application.photocontest.models.Rank;
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

    @Override
    public User getByUserName(String userName) {
        return userRepository.getByUserName(userName);
    }

    @Override
    public User getByEmail(String email) {
        return userRepository.getByEmail(email);
    }

    private void calculateUserRank(User user) {

        //TODO optimized
        /*
        Ranking:
         (0-50) points – Junkie
         (51 – 150) points – Enthusiast
         (151 – 1000) points – Master (can now be invited as jury)
         (1001 – infinity) points – Wise and Benevolent Photo Dictator (can still be jury)
         */

        /*switch (UserRanks.valueOf(user.getRank())){
            case JUNKIE:
                if (user.getPoints() > 50){
                    user.setRank(ENTHUSIAST.toString());
                }
                break;
            case ENTHUSIAST:
                if (user.getPoints() > 150){
                    user.setRank(MASTER.toString());
                }
                break;
            case MASTER:
                if (user.getPoints() > 1000){
                    user.setRank(DICTATOR.toString());
                }
                break;
            default:return;
        }*/


        if (user.getRank().getName().equals(JUNKIE.toString())) {
            if (user.getPoints() > 50) {
                user.setRank(userRepository.getRankByName(ENTHUSIAST.toString()));
                userRepository.update(user);
            }
            return;
        }
        if (user.getRank().getName().equals(ENTHUSIAST.toString())) {
            if (user.getPoints() > 150) {
                user.setRank(userRepository.getRankByName(MASTER.toString()));
                userRepository.update(user);
            }
            return;
        }
        if (user.getRank().getName().equals(MASTER.toString())) {
            if (user.getPoints() > 1000) {
                user.setRank(userRepository.getRankByName(DICTATOR.toString()));
                userRepository.update(user);
            }
            return;
        }

    }


    @Override
    public User create(User user) {
        return userRepository.create(user);
    }

    @Override
    public User update(User user) {
        return null;
    }

    @Override
    public void delete(int id) {
        return;
    }
}
