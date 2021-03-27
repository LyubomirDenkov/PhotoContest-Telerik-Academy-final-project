package application.photocontest.repository.contracts;

import application.photocontest.models.Contest;

public interface ContestRepository extends GetOperations<Contest>, CudOperations<Contest> {
}
