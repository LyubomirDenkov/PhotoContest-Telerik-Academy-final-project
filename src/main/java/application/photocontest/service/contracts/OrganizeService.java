package application.photocontest.service.contracts;

import application.photocontest.models.Organizer;

public interface OrganizeService extends GetServiceOperations<Organizer>,CudServiceOperations<Organizer>{

    Organizer getByUserName(String userName);

}
