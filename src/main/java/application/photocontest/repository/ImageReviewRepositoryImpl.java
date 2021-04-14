package application.photocontest.repository;

import application.photocontest.models.ImageReview;
import application.photocontest.repository.contracts.ImageReviewRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class ImageReviewRepositoryImpl implements ImageReviewRepository {

    private final SessionFactory sessionFactory;

    public ImageReviewRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }


    @Transactional
    @Override
    public void create(ImageReview imageReview) {

        try (Session session = sessionFactory.openSession()) {
            session.save(imageReview);
        }
    }

    @Override
    public List<ImageReview> getImageReviewByUserId(int id) {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from ImageReview where user.id = :id ",
                    ImageReview.class).setParameter("id", id).list();

        }
    }

    public ImageReview getImageReviewUserContestAndImageId(int userId,int contestId,int imageId){

        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from ImageReview where user.id = :userId " +
                            "and contest.id = :contestId and image.id = :imageId", ImageReview.class)
                    .setParameter("userId", userId)
                    .setParameter("contestId",contestId)
                    .setParameter("imageId",imageId).uniqueResult();

        }
    }

}
