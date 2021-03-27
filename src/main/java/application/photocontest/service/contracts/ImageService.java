package application.photocontest.service.contracts;

import application.photocontest.models.Image;

public interface ImageService extends GetServiceOperations<Image>, CudServiceOperations<Image> {
}
