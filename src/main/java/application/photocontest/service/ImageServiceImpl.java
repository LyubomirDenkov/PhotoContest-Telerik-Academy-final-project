package application.photocontest.service;

import application.photocontest.enums.UserRoles;
import application.photocontest.models.Image;
import application.photocontest.models.User;
import application.photocontest.repository.contracts.ImageRepository;
import application.photocontest.repository.contracts.UserRepository;
import application.photocontest.service.contracts.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

import static application.photocontest.service.authorization.AuthorizationHelper.verifyUserHasRoles;

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
    public Image getById(User userCredentials, int id) {
        return imageRepository.getById(id);
    }

    @Override
    public Image create(User user, Image image) {

        verifyUserHasRoles(user, UserRoles.USER,UserRoles.ORGANIZER);
        User userUploader = userRepository.getUserByUserName(user.getUserCredentials().getUserName());
        //image.setUploadedBy(userUploader);

        Image imageToCreate = imageRepository.create(image);

        Set<Image> images = userUploader.getImages();
        images.add(imageToCreate);
        userUploader.setImages(images);
        userRepository.update(userUploader);

        return imageToCreate;

    }


    @Override
    public void delete(User userCredentials, int id) {

        imageRepository.delete(id);
    }

    @Override
    public List<Image> getTopRatedPictures() {
        List<Image> images = imageRepository.getTopRatedPictures();

        if (images.size() > 6){
            return images.subList(0,6);
        }
        return images;
    }
}
