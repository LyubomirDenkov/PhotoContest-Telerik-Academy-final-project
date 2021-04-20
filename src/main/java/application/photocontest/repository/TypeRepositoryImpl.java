package application.photocontest.repository;

import application.photocontest.exceptions.EntityNotFoundException;
import application.photocontest.models.Type;
import application.photocontest.repository.contracts.TypeRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TypeRepositoryImpl implements TypeRepository {

    private final SessionFactory sessionFactory;

    @Autowired
    public TypeRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }


    @Override
    public List<Type> getAll() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from Type ", Type.class).list();
        }
    }

    @Override
    public Type getById(int id) {
        try (Session session = sessionFactory.openSession()) {

            Type type = session.get(Type.class, id);

            if (type == null) {
                throw new EntityNotFoundException("Type", id);
            }
            return type;

        }
    }


}
