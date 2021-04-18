package application.photocontest.repository;

import application.photocontest.exceptions.EntityNotFoundException;
import application.photocontest.models.Points;
import application.photocontest.models.User;
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
    public Points getById(int id) {

        try (Session session = sessionFactory.openSession()) {

            Points points = session.get(Points.class, id);

            if (points == null) {
                throw new EntityNotFoundException("Points", id);
            }
            return points;
        }
    }

    @Override
    public Points createPoints(Points points) {
        try (Session session = sessionFactory.openSession()) {

            session.save(points);

            return getById(points.getId());
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
