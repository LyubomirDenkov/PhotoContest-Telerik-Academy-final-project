package application.photocontest.service;

import application.photocontest.models.Image;
import application.photocontest.models.User;
import application.photocontest.models.UserCredentials;
import application.photocontest.repository.contracts.ImageRepository;
import application.photocontest.repository.contracts.UserRepository;
import application.photocontest.service.contracts.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class ImageServiceImpl implements ImageService {

    private final ImageRepository imageRepository;
    private final UserRepository userRepository;



    @Autowired
    public ImageServiceImpl(ImageRepository imageRepository, UserRepository userRepository) {
        this.imageRepository = imageRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Image getById(UserCredentials userCredentials, int id) {
        return imageRepository.getById(id);
    }

    @Override
    public Image create(UserCredentials userCredentials, Image image) {
                Image imageToCreate = imageRepository.create(image);
        User user = userRepository.getUserByUserName(userCredentials.getUserName());
        Set<Image> images = user.getUserCredentials().getImages();
        images.add(imageToCreate);
        userCredentials.setImages(images);

                userRepository.update(user);

                return imageToCreate;

    }


    @Override
    public void delete(UserCredentials userCredentials, int id) {
        imageRepository.delete(id);
    }
}
