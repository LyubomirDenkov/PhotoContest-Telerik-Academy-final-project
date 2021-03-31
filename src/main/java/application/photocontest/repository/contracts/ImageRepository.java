package application.photocontest.repository.contracts;

import application.photocontest.models.Image;

public interface ImageRepository {

    Image getById(int id);

    Image create(Image image);

    void delete(int id);
}
