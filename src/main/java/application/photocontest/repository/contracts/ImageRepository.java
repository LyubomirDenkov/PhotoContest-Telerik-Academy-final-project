package application.photocontest.repository.contracts;

import application.photocontest.models.Image;

public interface ImageRepository extends GetRepositoryOperations<Image>, CudRepositoryOperations<Image> {
}
