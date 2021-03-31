package application.photocontest.controllers.rest;

import application.photocontest.controllers.authentications.AuthenticationHelper;
import application.photocontest.modelmappers.ImageMapper;
import application.photocontest.models.Image;
import application.photocontest.models.UserCredentials;
import application.photocontest.service.contracts.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
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

    @GetMapping("/{id}")
    public Image getById(@RequestHeader HttpHeaders headers, @PathVariable int id){

        UserCredentials userCredentials = authenticationHelper.tryGetUser(headers);

        return imageService.getById(userCredentials,id);

    }

    @PostMapping
    public Image create(@RequestHeader HttpHeaders headers, @RequestParam(name = "file", required = false) Optional<MultipartFile> file,
                        @RequestParam(name = "url", required = false) Optional<String> url,
                        @RequestParam(name = "title") String title,
                        @RequestParam(name = "story") String story) throws IOException {

        UserCredentials userCredentials = authenticationHelper.tryGetUser(headers);

            String encodedImage = Base64.getEncoder().encodeToString(file.get().getBytes());

            Image image = imageMapper.toModel(title,story,encodedImage);

            return imageService.create(userCredentials,image);

    }

    @DeleteMapping("{id}")
    public void delete(@RequestHeader HttpHeaders headers,@PathVariable int id){

        UserCredentials userCredentials = authenticationHelper.tryGetUser(headers);

        imageService.delete(userCredentials,id);
    }
}
