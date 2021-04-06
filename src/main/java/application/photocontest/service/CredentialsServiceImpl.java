package application.photocontest.service;

import application.photocontest.models.UserCredentials;
import application.photocontest.repository.contracts.CredentialsRepository;
import application.photocontest.service.contracts.CredentialsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CredentialsServiceImpl implements CredentialsService {


    private final CredentialsRepository credentialsRepository;

    @Autowired
    public CredentialsServiceImpl(CredentialsRepository credentialsRepository) {
        this.credentialsRepository = credentialsRepository;

    }

    @Override
    public UserCredentials getByUserName(String userName) {
        return credentialsRepository.getByUsername(userName);
    }
}
