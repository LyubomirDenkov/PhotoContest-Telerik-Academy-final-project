package application.photocontest.service.contracts;

import application.photocontest.models.Image;
import application.photocontest.models.UserCredentials;

public interface ImageService extends CudServiceOperations<Image>{

    Image getById(UserCredentials userCredentials, int id);

}
