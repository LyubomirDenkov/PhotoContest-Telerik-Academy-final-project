package application.photocontest.services;

import application.photocontest.exceptions.UnauthorizedOperationException;
import application.photocontest.models.ImageReview;
import application.photocontest.models.Role;
import application.photocontest.models.User;
import application.photocontest.repository.contracts.ImageReviewRepository;
import application.photocontest.service.ImageReviewServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static application.photocontest.Helpers.createMockImageReview;
import static application.photocontest.Helpers.createMockUser;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ImageReviewServiceImplTests {


    @Mock
    ImageReviewRepository imageReviewRepository;

    @InjectMocks
    ImageReviewServiceImpl imageReviewService;


    @Test
    public void Should_ThrowException_When_UserDontHaveRole() {
        User user = new User();
        Set<Role> roles = new HashSet<>();
        user.setRoles(roles);

        Assertions.assertThrows(UnauthorizedOperationException.class,
                () -> imageReviewService.getAllReviewsByImageId(user, 1));

    }


    @Test
    public void Should_callRepository_When_InputIsValid() {
        ImageReview imageReview = createMockImageReview();
        User user = createMockUser();

        when(imageReviewRepository.getImageReviewByImageId(1)).thenReturn(List.of(imageReview));

        imageReviewService.getAllReviewsByImageId(user, 1);

        verify(imageReviewRepository, times(1)).getImageReviewByImageId(1);

    }

}
