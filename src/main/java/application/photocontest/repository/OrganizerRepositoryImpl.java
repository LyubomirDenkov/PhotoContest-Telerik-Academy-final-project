package application.photocontest.repository;

import application.photocontest.exceptions.EntityNotFoundException;
import application.photocontest.models.Organizer;
import application.photocontest.models.User;
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
    public Organizer create(Organizer organizer) {
        return null;
    }

    @Override
    public Organizer update(Organizer organizer) {
        return null;
    }

    @Override
    public void delete(int id) {

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
}
