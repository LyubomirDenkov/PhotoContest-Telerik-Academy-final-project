package application.photocontest.repository;

import application.photocontest.exceptions.EntityNotFoundException;
import application.photocontest.models.Phase;
import application.photocontest.repository.contracts.PhaseRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PhaseRepositoryImpl implements PhaseRepository {

    private final SessionFactory sessionFactory;

    @Autowired
    public PhaseRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Phase getPhaseByName(String phaseName) {

        try (Session session = sessionFactory.openSession()) {

            Query<Phase> query = session.createQuery("from Phase where name = :name ",Phase.class);
            query.setParameter("name",phaseName);

            List<Phase> phases = query.list();

            if (phases.isEmpty()) {
                throw new EntityNotFoundException("Phase","name",phaseName);
            }
            return phases.get(0);
        }
    }
}
