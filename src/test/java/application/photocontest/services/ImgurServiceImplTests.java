package application.photocontest.services;

import application.photocontest.service.ImgurServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;

import java.util.Optional;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class ImgurServiceImplTests {


    @InjectMocks
    ImgurServiceImpl imgurService;


    @Test
    public void Should_ThrowException_When_BothParametersArePresent() {

        MockMultipartFile multipartFile = new MockMultipartFile("data", "filename.kml",
                "text/plain", "some kml".getBytes());

        Assertions.assertThrows(UnsupportedOperationException.class,
                () -> imgurService.uploadImageToImgur(Optional.of(multipartFile), Optional.of("MockTestUrl")));

    }

    @Test
    public void Should_ThrowException_When_UrlIsNotValid() {

        Assertions.assertThrows(UnsupportedOperationException.class,
                () -> imgurService.uploadImageToImgur(Optional.empty(), Optional.of("MockTestUrl")));
    }

    @Test
    public void Should_ThrowException_When_UrlIsNotImage() {

        Assertions.assertThrows(UnsupportedOperationException.class,
                () -> imgurService.uploadImageToImgur(Optional.empty(), Optional.of("https://www.youtube.com/")));
    }

}
