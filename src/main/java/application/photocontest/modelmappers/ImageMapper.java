package application.photocontest.modelmappers;

import application.photocontest.models.Image;
import application.photocontest.repository.contracts.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ImageMapper {

    private final ImageRepository imageRepository;

    @Autowired
    public ImageMapper(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }


    public Image toModel(String title,String story,String imagePath) {

        Image image = new Image();

        image.setTitle(title);
        image.setStory(story);
        image.setUrl(imagePath);

        return image;
    }


}
