package application.photocontest.repository;

import application.photocontest.models.ImageReview;
import application.photocontest.repository.contracts.ImageReviewRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ImageReviewRepositoryImpl implements ImageReviewRepository {

    private final SessionFactory sessionFactory;

    public ImageReviewRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }


    @Override
    public List<ImageReview> getImageRatingsByUsername(String userName) {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from ImageReview where user.userCredentials.userName = :userName ",
                    ImageReview.class).setParameter("userName", userName).list();

        }
    }

}
