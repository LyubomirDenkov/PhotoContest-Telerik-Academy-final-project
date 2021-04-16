package application.photocontest.services;


import application.photocontest.models.Image;
import application.photocontest.models.User;
import application.photocontest.repository.contracts.ImageRepository;
import application.photocontest.repository.contracts.UserRepository;
import application.photocontest.service.ImageServiceImpl;
import application.photocontest.service.contracts.ImgurService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static application.photocontest.Helpers.createMockImage;
import static application.photocontest.Helpers.createMockUser;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ImageServiceImplTests {

    @Mock
    ImageRepository imageRepository;

    @Mock
    ImgurService imgurService;


    @Mock
    UserRepository userRepository;

    @InjectMocks
    ImageServiceImpl imageService;


    @Test
    public void getById_Should_Return_Image() {
        Image image = createMockImage();

        imageService.getById(image.getId());

        verify(imageRepository, times(1)).getById(image.getId());

    }

    @Test
    public void create_Should_Create_Image() throws IOException {
        User user = createMockUser();
        Image image = createMockImage();
        Image imageToAdd = createMockImage();
        imageToAdd.setId(10);
        imageToAdd.setUploader(user);
        Set<Image> imageSet = new HashSet<>();
        user.setImages(imageSet);

        when(userRepository.getById(user.getId())).thenReturn(user);

        when(imgurService.uploadImageToImgurAndReturnUrl(Optional.empty(), Optional.of(image.getUrl()))).thenReturn(image.getUrl());

        when(imageRepository.create(image)).thenReturn(image);

        imageService.create(user, image, Optional.empty(), Optional.of(image.getUrl()));

        verify(userRepository, times(1)).update(user);

    }

    @Test
    public void latestWinnerImages_Should_CallRepository_When_MethodIsCalled() {

        when(imageRepository.latestWinnerImages()).thenReturn(List.of(createMockImage()));

        imageService.latestWinnerImages();

        verify(imageRepository, times(1)).latestWinnerImages();

    }
}
