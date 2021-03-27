package application.photocontest.repository;

import application.photocontest.models.Image;
import application.photocontest.repository.contracts.ImageRepository;
import org.hibernate.SessionFactory;
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
        return null;
    }

    @Override
    public Image getById(int id) {
        return null;
    }

    @Override
    public Image create(Image name) {
        return null;
    }

    @Override
    public Image update(Image name) {
        return null;
    }

    @Override
    public void delete(int id) {

    }
}
