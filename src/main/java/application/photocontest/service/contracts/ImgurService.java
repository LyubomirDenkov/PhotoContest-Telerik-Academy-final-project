package application.photocontest.service.contracts;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

public interface ImgurService {

    String uploadImageToImgur(Optional<MultipartFile> file , Optional<String> url) throws IOException;

}
