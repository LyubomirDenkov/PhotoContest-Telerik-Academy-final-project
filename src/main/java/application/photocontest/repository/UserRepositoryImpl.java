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
    public List<User> getParticipantsFromContest(int id){

        try(Session session = sessionFactory.openSession()){

            Query<User> query = session.createQuery("select u from Contest c " +
                    "join c.participants as u " +
                    "where c.id = :id " +
                    "order by u.points desc ", User.class);

            query.setParameter("id",id);
            return query.list();
        }
    }

    @Override
    public User getUserByPictureId(int id){

        try(Session session = sessionFactory.openSession()){
            return session.createQuery("select u from User u " +
                    "join u.userCredentials.images as images " +
                    "where images.id = :id", User.class).setParameter("id",id).uniqueResult();
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
