package application.photocontest.repository;

import application.photocontest.exceptions.EntityNotFoundException;
import application.photocontest.models.*;
import application.photocontest.repository.contracts.UserRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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
    public UserCredentials getByUserName(String userName) {
        try (Session session = sessionFactory.openSession()) {

            Query<UserCredentials> query = session.createQuery("from UserCredentials " +
                    "where userName = :userName ", UserCredentials.class);

            query.setParameter("userName", userName);

            List<UserCredentials> result = query.list();

            if (result.isEmpty()) {

                throw new EntityNotFoundException("User/Organizer", "userName", userName);

            }
            return result.get(0);
        }
    }

    @Override
    public User getUserByUserName(String userName) {

        try (Session session = sessionFactory.openSession()) {

            Query<User> query = session.createQuery("from User " +
                    "where userCredentials.userName = :userName ", User.class);

            query.setParameter("userName", userName);

            List<User> result = query.list();

            if (result.isEmpty()) {

                throw new EntityNotFoundException("User", "userName", userName);

            }
            return result.get(0);
        }
    }

    @Override
    public Organizer getOrganizerByUserName(String userName) {
        try (Session session = sessionFactory.openSession()) {

            Query<Organizer> query = session.createQuery("from Organizer " +
                    "where userCredentials.userName = :userName ", Organizer.class);

            query.setParameter("userName", userName);

            List<Organizer> result = query.list();

            if (result.isEmpty()) {

                throw new EntityNotFoundException("Organizer", "userName", userName);

            }
            return result.get(0);
        }
    }

    @Override
    public User getByEmail(String email) {
        try (Session session = sessionFactory.openSession()) {

            Query<User> query = session.createQuery("from User " +
                    "where userCredentials.email = :email ", User.class);

            query.setParameter("email", email);

            List<User> result = query.list();

            if (result.isEmpty()) {

                throw new EntityNotFoundException("User", "email", email);

            }
            return result.get(0);
        }
    }

    @Override
    public Rank getRankByName(String name) {

        try (Session session = sessionFactory.openSession()) {

            Query<Rank> query = session.createQuery("from Rank " +
                    "where name = :name ", Rank.class);

            query.setParameter("name", name);

            List<Rank> result = query.list();

            if (result.isEmpty()) {

                throw new EntityNotFoundException("Rank", "name", name);

            }
            return result.get(0);
        }
    }

    @Override
    public Role getRoleByName(String name) {
        try (Session session = sessionFactory.openSession()) {

            Query<Role> query = session.createQuery("from Role " +
                    "where name = :name ", Role.class);

            query.setParameter("name", name);

            List<Role> result = query.list();

            if (result.isEmpty()) {

                throw new EntityNotFoundException("Role", "name", name);

            }
            return result.get(0);
        }
    }

    @Transactional
    @Override
    public User create(User user) {

        try (Session session = sessionFactory.openSession()) {

            session.save(user.getUserCredentials());
            session.save(user);

            return getById(user.getId());
        }
    }

    @Transactional
    @Override
    public User update(User user) {

        try (Session session = sessionFactory.openSession()) {

            session.beginTransaction();

            session.update(user.getUserCredentials());
            session.update(user);

            session.getTransaction().commit();
        }
        return user;
    }

    @Transactional
    @Override
    public void delete(int id) {

        User userToDelete = getById(id);

        try (Session session = sessionFactory.openSession()) {

            session.beginTransaction();

            session.delete(userToDelete);

            session.getTransaction().commit();
        }
    }
}
