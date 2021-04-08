package application.photocontest.service.contracts;

import java.io.IOException;

public interface ImgurService {

    String uploadImageToImgurAndReturnUrl(String image) throws IOException;

}
