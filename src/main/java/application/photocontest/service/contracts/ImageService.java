package application.photocontest.service.contracts;

import application.photocontest.models.Image;
import application.photocontest.models.User;
import application.photocontest.models.UserCredentials;

import java.util.List;

public interface ImageService {

    Image getById(User user, int id);

    Image create(User user, Image image);

    void delete(User user, int id);

    List<Image> getTopRatedPictures();

}
