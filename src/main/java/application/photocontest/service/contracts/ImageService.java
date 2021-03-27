package application.photocontest.service.contracts;

import application.photocontest.models.Image;

public interface ImageService extends GetOperations<Image>, CudOperations<Image>{
}
