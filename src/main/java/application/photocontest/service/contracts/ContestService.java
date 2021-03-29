package application.photocontest.service.contracts;

import application.photocontest.models.Contest;
import application.photocontest.models.User;



public interface ContestService extends GetServiceOperations<Contest>,CudServiceOperations<Contest> {

}
