package application.photocontest.repository;

import application.photocontest.exceptions.EntityNotFoundException;
import application.photocontest.models.User;
import application.photocontest.repository.contracts.UserRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
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
        try (Session session = sessionFactory.openSession()) {

            Query<User> query = session.createQuery("from User ", User.class);

            return query.list();
        }
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
    public User getByUserName(String userName) {
        try (Session session = sessionFactory.openSession()) {

            Query<User> query = session.createQuery("from User " +
                    "where userName = :userName ", User.class);

            query.setParameter("userName", userName);

            List<User> result = query.list();

            if (result.isEmpty()) {

                throw new EntityNotFoundException("User", "userName", userName);

            }
            return result.get(0);
        }
    }

    @Override
    public User getByEmail(String email) {
        try (Session session = sessionFactory.openSession()) {

            Query<User> query = session.createQuery("from User " +
                    "where email = :email ", User.class);

            query.setParameter("email", email);

            List<User> result = query.list();

            if (result.isEmpty()) {

                throw new EntityNotFoundException("User", "email", email);

            }
            return result.get(0);
        }
    }

    @Override
    public User update(User name) {
        return null;
    }

    @Override
    public void delete(int id) {

    }
}
