package application.photocontest.repository;

import application.photocontest.exceptions.EntityNotFoundException;
import application.photocontest.models.Image;
import application.photocontest.models.ImageReview;
import application.photocontest.repository.contracts.ImageRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class ImageRepositoryImpl implements ImageRepository {

    private final SessionFactory sessionFactory;

    @Autowired
    public ImageRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Image getById(int id) {
        try(Session session = sessionFactory.openSession()){

            Image image = session.get(Image.class,id);

            if (image == null){
                throw new EntityNotFoundException("Image",id);
            }
            return image;
        }
    }


    @Transactional
    @Override
    public void createJurorRateEntity(ImageReview imageReview) {

        try (Session session = sessionFactory.openSession()) {
            session.save(imageReview);
        }
    }

    @Override
    public List<ImageReview> getImageRatingsByUsername(String userName) {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from ImageReview where user.userCredentials.userName = :userName ",
                    ImageReview.class).setParameter("userName", userName).list();

        }
    }

    @Override
    public Long getReviewPointsByImageId(int id) {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("select sum(points) from ImageReview where image.id = :id ",
                    Long.class).setParameter("id", id).uniqueResult();
        }
    }

    public Long getReviewsCountByContestAndImageId(int contestId,int imageId){
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("select count(*) from ImageReview where image.id = :imageId and " +
                            "contest.id = :contestId ",
                    Long.class).setParameter("imageId", imageId).setParameter("contestId",contestId).uniqueResult();
        }
    }

    @Transactional
    @Override
    public Image create(Image image) {

        try (Session session = sessionFactory.openSession()) {

            session.save(image);

            return getById(image.getId());
        }
    }


    @Transactional
    @Override
    public Image update(Image image) {
        try (Session session = sessionFactory.openSession()) {

            session.beginTransaction();

            session.update(image);

            session.getTransaction().commit();
        }
        return image;
    }
    @Transactional
    @Override
    public void delete(int id) {

        Image imageToDelete = getById(id);

        try (Session session = sessionFactory.openSession()) {

            session.beginTransaction();

            session.delete(imageToDelete);

            session.getTransaction().commit();
        }
    }
}
