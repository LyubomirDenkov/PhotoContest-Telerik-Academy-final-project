package application.photocontest.controllers.rest;

import application.photocontest.controllers.authentications.AuthenticationHelper;
import application.photocontest.exceptions.EntityNotFoundException;
import application.photocontest.exceptions.UnauthorizedOperationException;
import application.photocontest.modelmappers.ImageMapper;
import application.photocontest.models.Image;
import application.photocontest.models.User;
import application.photocontest.models.UserCredentials;
import application.photocontest.models.dto.ImageDto;
import application.photocontest.service.contracts.ImageService;
import okhttp3.*;
import okhttp3.RequestBody;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.*;
import java.util.*;


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
    public Image getById(@RequestHeader HttpHeaders headers, @PathVariable int id) {
        User user = authenticationHelper.tryGetUser(headers);

        try {
            return imageService.getById(user, id);
        } catch (UnauthorizedOperationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PostMapping(value = "/upload", consumes = {"multipart/form-data", "application/json"})
    public Image create(@RequestHeader HttpHeaders headers,
                        @RequestParam(name = "file", required = false) Optional<MultipartFile> file,
                        @RequestParam(name = "url", required = false) Optional<String> url,
                        @RequestPart("dto") ImageDto dto) throws IOException {

        User user = authenticationHelper.tryGetUser(headers);
        Image image = imageMapper.fromDto(user, dto);
        try {
            return imageService.create(user, image, file, url);
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }
}
