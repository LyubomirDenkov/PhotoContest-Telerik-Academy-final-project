package application.photocontest.repository;

import application.photocontest.exceptions.EntityNotFoundException;
import application.photocontest.models.*;
import application.photocontest.repository.contracts.ContestRepository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ContestRepositoryImpl implements ContestRepository {

    private final SessionFactory sessionFactory;

    @Autowired
    public ContestRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }


    @Override
    public List<Contest> getAll() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from Contest ", Contest.class).list();
        }
    }

    @Override
    public Contest getById(int id) {


            try(Session session = sessionFactory.openSession()){

                Contest contest = session.get(Contest.class,id);

                if (contest == null){
                    throw new EntityNotFoundException("Contest",id);
                }
                return contest;
            }
    }


    @Override
    public Contest create(Contest contest) {

        try (Session session = sessionFactory.openSession()) {
            session.save(contest);

            return contest;
        }
    }

    @Override
    public Contest update(Contest contest) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            session.update(contest);

            session.getTransaction().commit();
        }
        return contest;
    }

    @Override
    public void delete(int id) {

    }

    @Override
    public Contest getByTitle(String title) {
        try (Session session = sessionFactory.openSession()) {

            Query<Contest> query = session.createQuery("from Contest " +
                    "where title = :title ", Contest.class);

            query.setParameter("title", title);

            List<Contest> result = query.list();

            if (result.isEmpty()) {

                throw new EntityNotFoundException("Contest", "title", title);

            }
            return result.get(0);
        }
    }

    @Override
    public List<User> getContestJury() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("select u from User u join u.roles as r " +
                    "where r.name = 'organizer' ", User.class).list();
        }
    }



    @Override
    public List<Contest> getOngoingContests() {

        try (Session session = sessionFactory.openSession()) {

            return session.createQuery("from Contest where phase.id = 1 ",Contest.class).list();

        }
    }

    @Override
    public List<Contest> getFinishedContests() {
        try (Session session = sessionFactory.openSession()) {

            return session.createQuery("from Contest where phase.id = 3 ",Contest.class).list();

        }
    }



}
