package application.photocontest.repository.contracts;

import application.photocontest.models.Image;
import application.photocontest.models.UserCredentials;

public interface ImageRepository {

    Image getById(int id);

    Image create(Image image);

    void delete(int id);
}
