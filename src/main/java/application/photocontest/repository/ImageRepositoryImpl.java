package application.photocontest.repository;

import application.photocontest.exceptions.EntityNotFoundException;
import application.photocontest.models.Image;
import application.photocontest.models.User;
import application.photocontest.repository.contracts.ImageRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ImageRepositoryImpl implements ImageRepository {

    private final SessionFactory sessionFactory;

    @Autowired
    public ImageRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }


    @Override
    public List<Image> getAll() {
        try (Session session = sessionFactory.openSession()) {

            Query<Image> query = session.createQuery("from Image ", Image.class);

            return query.list();
        }
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

    @Override
    public Image create(Image image) {
        try (Session session = sessionFactory.openSession()) {

            session.save(image);

            return getById(image.getId());
        }
    }

    @Override
    public Image update(Image image) {
        try (Session session = sessionFactory.openSession()) {

            session.beginTransaction();

            session.update(image);

            session.getTransaction().commit();
        }
        return image;
    }

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
