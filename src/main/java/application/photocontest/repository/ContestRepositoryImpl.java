package application.photocontest.repository;

import application.photocontest.exceptions.EntityNotFoundException;
import application.photocontest.models.Category;
import application.photocontest.models.Contest;
import application.photocontest.repository.contracts.ContestRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
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
        try (Session session = sessionFactory.openSession()) {

            Contest contest = session.get(Contest.class, id);

            if (contest == null) {

                throw new EntityNotFoundException("Contest", id);

            }
            return contest;
        }
    }

    @Override
    public Contest create(Contest name) {
        return null;
    }

    @Override
    public Contest update(Contest name) {
        return null;
    }

    @Override
    public void delete(int id) {

    }
}
