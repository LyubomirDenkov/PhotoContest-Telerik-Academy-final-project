package application.photocontest.repository;

import application.photocontest.exceptions.EntityNotFoundException;
import application.photocontest.models.ImageReview;
import application.photocontest.models.User;
import application.photocontest.repository.contracts.ImageReviewRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class ImageReviewRepositoryImpl implements ImageReviewRepository {

    private final SessionFactory sessionFactory;

    public ImageReviewRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }


    @Override
    public ImageReview create(ImageReview imageReview) {

        try (Session session = sessionFactory.openSession()) {
            session.save(imageReview);

            return imageReview;
        }
    }

    @Override
    public List<ImageReview> getImageReviewByImageId(int id) {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from ImageReview where image.id = :id ",
                    ImageReview.class).setParameter("id", id).list();

        }
    }

    public ImageReview getImageReviewUserContestAndImageId(int userId, int contestId, int imageId) {

        try (Session session = sessionFactory.openSession()) {
            Query<ImageReview> query = session.createQuery("from ImageReview where user.id = :userId " +
                    "and contest.id = :contestId and image.id = :imageId", ImageReview.class)
                    .setParameter("userId", userId)
                    .setParameter("contestId", contestId)
                    .setParameter("imageId", imageId);

            List<ImageReview> imageReviewList = query.list();

            if (imageReviewList.isEmpty()) {
                throw new EntityNotFoundException("Review", "id parameters", "contest,image and user");
            }
            return imageReviewList.get(0);
        }
    }

    @Override
    public Long getImageReviewPointsByContestAndImageId(int contestId, int imageId) {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("select sum(points) from ImageReview " +
                    "where image.id = :imageId and contest.id = :contestId ", Long.class)
                    .setParameter("imageId", imageId)
                    .setParameter("contestId", contestId)
                    .uniqueResult();
        }
    }

    @Override
    public Long getReviewsCountByContestAndImageId(int contestId, int imageId) {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("select count(*) from ImageReview " +
                    "where image.id = :imageId and contest.id = :contestId ", Long.class)
                    .setParameter("imageId", imageId)
                    .setParameter("contestId", contestId)
                    .uniqueResult();
        }
    }

    @Transactional
    @Override
    public void delete(ImageReview imageReview) {

        try (Session session = sessionFactory.openSession()) {

            session.beginTransaction();

            session.delete(imageReview);

            session.getTransaction().commit();
        }
    }

}
