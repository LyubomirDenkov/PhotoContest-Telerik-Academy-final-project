package application.photocontest.repository.contracts;

import application.photocontest.models.Contest;

public interface ContestRepository extends GetRepositoryOperations<Contest>,CudRepositoryOperations<Contest> {
}
