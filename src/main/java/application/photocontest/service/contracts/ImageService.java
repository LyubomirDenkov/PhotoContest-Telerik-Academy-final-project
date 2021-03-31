package application.photocontest.service.contracts;

import application.photocontest.models.Image;
import application.photocontest.models.UserCredentials;

public interface ImageService {

    Image getById(UserCredentials userCredentials, int id);

    Image create(UserCredentials userCredentials, Image image);

    void delete(UserCredentials userCredentials,int id);
}
