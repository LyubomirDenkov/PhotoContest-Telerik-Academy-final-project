package application.photocontest.repository;

import application.photocontest.exceptions.EntityNotFoundException;
import application.photocontest.models.Image;
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

    @Override
    public List<Image> latestWinnerImages() {

        try (Session session = sessionFactory.openSession()) {

            return session.createQuery("select wi from Contest c " +
                    "join c.winnerImages as wi " +
                    "where c.phase.name = 'finished' " +
                    "order by c.timeTillFinished ", Image.class).list();

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
}
