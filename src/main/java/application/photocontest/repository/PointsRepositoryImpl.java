package application.photocontest.repository;

import application.photocontest.models.Points;
import application.photocontest.repository.contracts.PointsRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class PointsRepositoryImpl implements PointsRepository {


    private final SessionFactory sessionFactory;

    @Autowired
    public PointsRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void createPoints(Points points) {
        try (Session session = sessionFactory.openSession()) {

            session.save(points);

        }
    }

    @Transactional
    @Override
    public void updatePoints(Points points) {
        try (Session session = sessionFactory.openSession()) {

            session.beginTransaction();
            session.update(points);
            session.getTransaction().commit();
        }

    }
}
