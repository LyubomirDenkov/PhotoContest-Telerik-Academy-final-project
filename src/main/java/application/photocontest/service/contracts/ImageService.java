package application.photocontest.service.contracts;

import application.photocontest.models.Image;
import application.photocontest.models.User;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface ImageService {

    Image getById(User user, int id);

    Image create(User user, Image image, Optional<MultipartFile> file, Optional<String> url) throws IOException;

    void delete(User user, int id);

}
