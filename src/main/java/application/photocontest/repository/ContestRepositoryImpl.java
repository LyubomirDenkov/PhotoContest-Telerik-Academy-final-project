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
import java.util.Optional;

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
            return session.createQuery("from Contest ", Contest.class)
                    .list();
        }
    }

    public List<Contest> getContestInPhaseOneAndPhaseTwo() {

        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from Contest where phase.name like 'ongoing' or phase.name like 'voting' ", Contest.class)
                    .list();
        }
    }

    @Override
    public Contest getById(int id) {


        try (Session session = sessionFactory.openSession()) {

            Query<Contest> query = session.createQuery("from Contest " +
                    "where id = :id ", Contest.class);

            query.setParameter("id", id);

            List<Contest> result = query.list();

            if (result.isEmpty()) {

                throw new EntityNotFoundException("Contest", "id", String.valueOf(id));

            }
            return result.get(0);
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


    public List<Contest> getOngoingContests(boolean isOrganizer) {

        StringBuilder query = new StringBuilder();
        query.append("from Contest where phase.name like 'ongoing' ");

        if (!isOrganizer) {
            query.append(" and type.name like 'open'");
        }

        try (Session session = sessionFactory.openSession()) {

            return session.createQuery(query.toString(), Contest.class).list();

        }
    }

    public List<Contest> getFinishedContests(int userId, boolean isOrganizer) {

        StringBuilder query = new StringBuilder();

        if (!isOrganizer) {
            query.append("select c from Contest c join c.participants as user where user.id = ")
                    .append(userId)
                    .append("  and c.phase.name = 'finished'");
        } else {
            query.append("from Contest where phase.name = 'finished'");
        }


        try (Session session = sessionFactory.openSession()) {

            return session.createQuery(query.toString(), Contest.class).list();

        }
    }


    public List<Contest> getVotingContests(int userId, boolean isOrganizer) {

        StringBuilder query = new StringBuilder();

        if (!isOrganizer) {
            query.append(String.format("select c from Contest c join c.jury as jury where jury.id = %d ", userId))
                    .append(" and c.phase.name like 'voting'");
        } else {
            query.append("from Contest where phase.name like 'voting'");
        }


        try (Session session = sessionFactory.openSession()) {
            return session.createQuery(query.toString(), Contest.class).list();
        }
    }


    @Override
    public List<User> getContestParticipants(int contestId) {

        try (Session session = sessionFactory.openSession()) {

            return session.createQuery("select p from Contest c " +
                    "join c.participants as p " +
                    "where c.id = :id ", User.class).setParameter("id", contestId).list();

        }
    }

    @Override
    public List<Image> getContestImages(int contestId) {

        try (Session session = sessionFactory.openSession()) {

            return session.createQuery("select i from Contest c " +
                    "join c.images as i " +
                    "where c.id = :id ", Image.class).setParameter("id", contestId).list();

        }
    }

    @Override
    public Contest getContestByImageUploaderId(int contestId, int userId) {

        try (Session session = sessionFactory.openSession()) {

            Query<Contest> query = session.createQuery("select c from Contest c " +
                    "join c.images as image " +
                    "where c.id = :contestId and " +
                    "image.uploader.id = :userId ", Contest.class);

            query.setParameter("contestId", contestId);
            query.setParameter("userId", userId);

            List<Contest> result = query.list();

            if (result.isEmpty()) {

                throw new EntityNotFoundException("Contest", "user", String.valueOf(userId));

            }
            return result.get(0);

        }
    }

    @Override
    public List<Contest> getByUserId(int id) {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("select c from Contest c join c.participants as user " +
                    " where user.id = :id ", Contest.class).setParameter("id", id).list();
        }
    }

    public List<Contest> getUserContests(int id, Optional<String> phase) {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("select c from Contest c join c.participants as participant " +
                    "where participant.id = :id " +
                    "and c.phase.name like concat('%', :phase ,'%')", Contest.class)
                    .setParameter("id", id)
                    .setParameter("phase", phase.orElse(""))
                    .list();
        }
    }

    @Override
    public List<Contest> mainPageOngoingContests() {
        try (Session session = sessionFactory.openSession()) {

            return session.createQuery("from Contest where phase.name like 'ongoing'" +
                            " and type.name like 'open'",
                    Contest.class).list();

        }
    }
}
