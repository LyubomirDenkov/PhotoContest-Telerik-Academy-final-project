package application.photocontest.repository;

import application.photocontest.exceptions.EntityNotFoundException;
import application.photocontest.models.User;
import application.photocontest.repository.contracts.UserRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserRepositoryImpl implements UserRepository {

    private final SessionFactory sessionFactory;

    @Autowired
    public UserRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<User> getAll() {
        return null;
    }

    @Override
    public User getById(int id) {

        try(Session session = sessionFactory.openSession()){

            User user = session.get(User.class,id);

            if (user == null){
                throw new EntityNotFoundException("User",id);
            }
            return user;
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
