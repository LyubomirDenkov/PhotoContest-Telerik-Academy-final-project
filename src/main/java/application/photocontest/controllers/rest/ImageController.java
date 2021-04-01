package application.photocontest.controllers.rest;

import application.photocontest.controllers.authentications.AuthenticationHelper;
import application.photocontest.modelmappers.ImageMapper;
import application.photocontest.models.Image;
import application.photocontest.models.UserCredentials;
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


    private static final String IMGUR_IMAGE_UPLOAD_URL = "https://api.imgur.com/3/image";
    private static final String IMGUR_CLIENT_ID = "Client-ID 442f5d37036bc37";
    private static final String IMGUR_AUTHORIZATION = "Authorization";
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

        UserCredentials userCredentials = authenticationHelper.tryGetUser(headers);

        return imageService.getById(userCredentials, id);

    }

    @PostMapping
    public Image create(@RequestHeader HttpHeaders headers,
                        @RequestParam(name = "file", required = false) Optional<MultipartFile> file,
                        @RequestParam(name = "url", required = false) Optional<String> url,
                        @RequestParam(name = "title") String title,
                        @RequestParam(name = "story") String story) throws IOException {

        UserCredentials userCredentials = authenticationHelper.tryGetUser(headers);

        if ((file.isEmpty() && url.isEmpty()) || (file.isPresent() && url.isPresent())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,"something");
        }

        String imageLink = "";

        if (file.isPresent()){
            String inputImage = Base64.getEncoder().encodeToString(file.get().getBytes());
            imageLink = uploadImageToImgurAndReturnUrl(inputImage);
        }
        if (url.isPresent()){
            imageLink = uploadImageToImgurAndReturnUrl(url.get());
        }


        Image image = imageMapper.toModel(title, story, imageLink);

        return imageService.create(userCredentials, image);
    }

    private String uploadImageToImgurAndReturnUrl(String image) throws IOException {

        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("image", image)
                .build();
        Request request = new Request.Builder()
                .url(IMGUR_IMAGE_UPLOAD_URL)
                .method("POST", body)
                .addHeader(IMGUR_AUTHORIZATION, IMGUR_CLIENT_ID)
                .build();

        Response response = client.newCall(request).execute();

        JSONObject jsonObject = new JSONObject(response.body().string());
        JSONObject jsonObjectData = jsonObject.getJSONObject("data");
        return jsonObjectData.getString("link");
    }

    @DeleteMapping("{id}")
    public void delete(@RequestHeader HttpHeaders headers, @PathVariable int id) {

        UserCredentials userCredentials = authenticationHelper.tryGetUser(headers);

        imageService.delete(userCredentials, id);
    }
}
