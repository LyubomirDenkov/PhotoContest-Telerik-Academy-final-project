package application.photocontest.controllers.rest;

import application.photocontest.controllers.authentications.AuthenticationHelper;
import application.photocontest.modelmappers.ImageMapper;
import application.photocontest.models.Image;
import application.photocontest.models.User;
import application.photocontest.models.UserCredentials;
import application.photocontest.service.contracts.ImageService;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.Optional;

@RestController
@RequestMapping("/api/images")
public class ImageController {


    private final ImageService imageService;
    private final ImageMapper imageMapper;
    private final AuthenticationHelper authenticationHelper;

    @Autowired
    public ImageController(ImageService imageService, ImageMapper imageMapper, AuthenticationHelper authenticationHelper) {
        this.imageService = imageService;
        this.imageMapper = imageMapper;
        this.authenticationHelper = authenticationHelper;
    }

    @PostMapping
    public Image create(@RequestHeader HttpHeaders headers, @RequestParam(name = "file", required = false) Optional<MultipartFile> file,
                        @RequestParam(name = "url", required = false) Optional<String> url,
                        @RequestParam(name = "title") String title,
                        @RequestParam(name = "story") String story) throws IOException {

        UserCredentials userCredentials = authenticationHelper.tryGetUser(headers);
        String dir = System.getProperty("user.dir");
        dir += "\\imagesDir\\";
        byte[] bytes = file.get().getBytes();
        Path path = Paths.get(dir + file.get().getOriginalFilename());
        Files.write(path,bytes);

        if (file.isPresent()) {

            Image image = imageMapper.toModel(title,story,dir);

            return imageService.create(userCredentials,image);
        }else {
            throw new EOFException();
        }

    }

}