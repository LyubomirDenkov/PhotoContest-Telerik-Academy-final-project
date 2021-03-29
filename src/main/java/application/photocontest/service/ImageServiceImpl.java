package application.photocontest.service;

import application.photocontest.models.Image;
import application.photocontest.models.User;
import application.photocontest.models.UserCredentials;
import application.photocontest.repository.contracts.ImageRepository;
import application.photocontest.repository.contracts.UserRepository;
import application.photocontest.service.contracts.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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

    //TODO - who can delete ||| update
    @Override
    public List<Image> getAll(UserCredentials userCredentials) {
        return imageRepository.getAll();
    }

    @Override
    public Image getById(UserCredentials userCredentials, int id) {
        return imageRepository.getById(id);
    }

    @Override
    public Image create(UserCredentials userCredentials, Image image) {

        Image imageToCreate = imageRepository.create(image);
        //addImageToUserImages(user,image);
        return imageToCreate;
    }

    public void addImageToUserImages(User user,Image image){
        Set<Image> images = user.getImages();
        images.add(image);
        userRepository.update(user);
    }

    @Override
    public Image update(UserCredentials userCredentials,Image image) {
        return imageRepository.update(image);
    }

    @Override
    public void delete(UserCredentials userCredentials, int id) {
        imageRepository.delete(id);
    }
}
