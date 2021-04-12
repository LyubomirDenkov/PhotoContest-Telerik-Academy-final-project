package application.photocontest.modelmappers;

import application.photocontest.models.Image;
import application.photocontest.models.User;
import application.photocontest.models.UserCredentials;
import application.photocontest.models.dto.ImageDto;
import application.photocontest.repository.contracts.ImageRepository;
import application.photocontest.repository.contracts.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ImageMapper {




    public ImageMapper() {

    }


    public Image fromDto(User user, ImageDto dto) {

        Image image = new Image();

        image.setTitle(dto.getTitle());
        image.setStory(dto.getStory());
        image.setUploader(user);

        return image;
    }


}
