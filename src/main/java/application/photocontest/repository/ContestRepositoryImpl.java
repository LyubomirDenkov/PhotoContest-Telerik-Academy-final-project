package application.photocontest.repository;

import application.photocontest.models.Contest;
import application.photocontest.repository.contracts.ContestRepository;
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
        return null;
    }

    @Override
    public Contest getById(int id) {
        return null;
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
