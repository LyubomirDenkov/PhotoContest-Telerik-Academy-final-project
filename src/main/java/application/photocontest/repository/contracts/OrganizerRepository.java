package application.photocontest.repository.contracts;

import application.photocontest.models.Organizer;

public interface OrganizerRepository extends GetRepositoryOperations<Organizer>,CudRepositoryOperations<Organizer>{

    Organizer getByUserName(String userName);

}