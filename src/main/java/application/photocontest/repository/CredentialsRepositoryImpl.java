package application.photocontest.repository;

import application.photocontest.exceptions.EntityNotFoundException;
import application.photocontest.models.UserCredentials;
import application.photocontest.repository.contracts.CredentialsRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CredentialsRepositoryImpl implements CredentialsRepository {


    private final SessionFactory sessionFactory;

    @Autowired
    public CredentialsRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }


    @Override
    public UserCredentials getByUsername(String userName) {
        try (Session session = sessionFactory.openSession()) {

            Query<UserCredentials> query = session.createQuery("from UserCredentials " +
                    "where userName = :userName ", UserCredentials.class);

            query.setParameter("userName", userName);

            List<UserCredentials> result = query.list();

            if (result.isEmpty()) {

                throw new EntityNotFoundException("User", "userName", userName);

            }
            return result.get(0);
        }
    }
}
