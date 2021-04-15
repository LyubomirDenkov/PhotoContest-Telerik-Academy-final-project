package application.photocontest.service;

import application.photocontest.enums.UserRoles;
import application.photocontest.models.Image;
import application.photocontest.models.User;
import application.photocontest.repository.contracts.ImageRepository;
import application.photocontest.repository.contracts.UserRepository;
import application.photocontest.service.contracts.ImageService;
import application.photocontest.service.contracts.ImgurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;
import java.util.Set;

import static application.photocontest.service.authorization.AuthorizationHelper.verifyUserHasRoles;

@Service
public class ImageServiceImpl implements ImageService {

    private final ImageRepository imageRepository;
    private final UserRepository userRepository;
    private final ImgurService imgurService;


    @Autowired
    public ImageServiceImpl(ImageRepository imageRepository, UserRepository userRepository, ImgurService imgurService) {
        this.imageRepository = imageRepository;
        this.userRepository = userRepository;
        this.imgurService = imgurService;
    }

    @Override
    public Image getById(int id) {
        return imageRepository.getById(id);
    }

    @Override
    public Image create(User user, Image image, Optional<MultipartFile> file, Optional<String> url) throws IOException {

        verifyUserHasRoles(user, UserRoles.USER,UserRoles.ORGANIZER);
        User userUploader = userRepository.getById(user.getId());
        image.setUploader(userUploader);

        String imageUrl =  imgurService.uploadImageToImgurAndReturnUrl(file,url);
        image.setUrl(imageUrl);

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

}
