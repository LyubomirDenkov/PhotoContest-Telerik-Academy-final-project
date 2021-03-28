package application.photocontest.service;

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

    private static final int JUNKIE_CEILING_POINTS = 50;
    private static final int ENTHUSIAST_CEILING_POINTS = 150;
    private static final int MASTER_CEILING_POINTS = 1000;
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

        if (user.getRank().getName().equals(DICTATOR.toString())){
            return;
        }

        if (user.getRank().getName().equals(JUNKIE.toString())) {
            if (isUserHavePointsToUpgradeRank(user,JUNKIE_CEILING_POINTS)) {
              setNewUserRank(user, ENTHUSIAST.toString());
            }
        }
        if (user.getRank().getName().equals(ENTHUSIAST.toString())) {
            if (isUserHavePointsToUpgradeRank(user,ENTHUSIAST_CEILING_POINTS)) {
                setNewUserRank(user, MASTER.toString());
            }
        }
        if (user.getRank().getName().equals(MASTER.toString())) {
            if (isUserHavePointsToUpgradeRank(user,MASTER_CEILING_POINTS)) {
                setNewUserRank(user, DICTATOR.toString());
            }
        }
    }

    private boolean isUserHavePointsToUpgradeRank(User user, int points){
        return user.getPoints() > points;
    }
    private void setNewUserRank(User user,String rank){
        Rank newRank = userRepository.getRankByName(rank);
        user.setRank(newRank);
        userRepository.update(user);
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
