package application.photocontest.service.contracts;

import application.photocontest.models.Organizer;

public interface OrganizerService extends GetServiceOperations<Organizer>,CudServiceOperations<Organizer>{

    Organizer getByUserName(String userName);

}
