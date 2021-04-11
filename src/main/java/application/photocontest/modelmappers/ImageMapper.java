package application.photocontest.modelmappers;

import application.photocontest.models.Image;
import application.photocontest.models.User;
import application.photocontest.models.UserCredentials;
import application.photocontest.repository.contracts.ImageRepository;
import application.photocontest.repository.contracts.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ImageMapper {




    public ImageMapper() {

    }


    public Image fromDto(User user, String title, String story) {

        Image image = new Image();

        image.setTitle(title);
        image.setStory(story);
        image.setUploader(user);

        return image;
    }


}
