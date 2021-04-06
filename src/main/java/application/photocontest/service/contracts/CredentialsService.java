package application.photocontest.service.contracts;

import application.photocontest.models.UserCredentials;

public interface CredentialsService {

    UserCredentials getByUserName(String userName);

}
