package application.photocontest.repository.contracts;

import application.photocontest.models.UserCredentials;

public interface CredentialsRepository {
    UserCredentials getByUsername(String userName);
}
