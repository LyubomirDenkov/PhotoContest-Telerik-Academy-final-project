package application.photocontest.repository;

import application.photocontest.exceptions.EntityNotFoundException;
import application.photocontest.models.Organizer;
import application.photocontest.repository.contracts.OrganizerRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class OrganizerRepositoryImpl implements OrganizerRepository {

    private final SessionFactory sessionFactory;

    @Autowired
    public OrganizerRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<Organizer> getAll() {
        try (Session session = sessionFactory.openSession()) {

            Query<Organizer> query = session.createQuery("from Organizer ", Organizer.class);

            return query.list();
        }
    }

    @Override
    public Organizer getById(int id) {
        try(Session session = sessionFactory.openSession()){

            Organizer organizer = session.get(Organizer.class,id);

            if (organizer == null){
                throw new EntityNotFoundException("Organizer",id);
            }
            return organizer;
        }
    }


    @Override
    public Organizer getByUserName(String userName) {
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
    public Organizer create(Organizer organizer) {
        try (Session session = sessionFactory.openSession()) {

            session.save(organizer.getCredentials());
            session.save(organizer);

            return getById(organizer.getId());
        }
    }

    @Override
    public Organizer update(Organizer organizer) {
        try (Session session = sessionFactory.openSession()) {

            session.beginTransaction();

            session.update(organizer.getCredentials());
            session.update(organizer);

            session.getTransaction().commit();
        }
        return organizer;
    }

    @Override
    public void delete(int id) {

        Organizer organizerToDelete = getById(id);

        try (Session session = sessionFactory.openSession()) {

            session.beginTransaction();

            session.delete(organizerToDelete);

            session.getTransaction().commit();
        }
    }
}
