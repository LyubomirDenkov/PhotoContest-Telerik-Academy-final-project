package application.photocontest.repository.contracts;

import application.photocontest.models.Image;

public interface ImageRepository extends GetOperations<Image>, CudOperations<Image>{
}
