package application.photocontest.service;

import application.photocontest.service.contracts.ImgurService;
import okhttp3.*;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;
import java.util.Optional;

import static application.photocontest.constants.Constants.*;

@Service
public class ImgurServiceImpl implements ImgurService {


    public String uploadImageToImgur(Optional<MultipartFile> file, Optional<String> url) throws IOException {

        if (file.isPresent()) {
            file = validateFileIsNotBlank(file);
        }
        if (url.isPresent()) {
            url = validateUrlIsNotBlank(url);
        }
        if (file.isPresent() && url.isPresent()) {
            throw new UnsupportedOperationException(ONLY_LOCAL_FILE_OR_URL_ERROR_MESSAGE);
        } else if (file.isEmpty() && url.isEmpty()) {
            return "";
        }
        String image = "";
        if (file.isPresent()) {
            image = Base64.getEncoder().encodeToString(file.get().getBytes());
        } else {
            validateUrl(url);
            image = url.get();
        }
        return upload(image);
    }

    private void validateUrl(Optional<String> url) {
        try {
            URL imageUrl = new URL(url.get());
            HttpURLConnection huc = (HttpURLConnection) imageUrl.openConnection();
            validateStatusCodeIsSuccess(huc.getResponseCode());
        } catch (IOException e) {
            throw new UnsupportedOperationException(URL_IS_NOT_VALID_ERROR_MESSAGE);
        }
    }

    private Optional<MultipartFile> validateFileIsNotBlank(Optional<MultipartFile> file) {
        if (file.get().getOriginalFilename().isBlank()) {
            return Optional.empty();
        }
        return file;
    }

    private Optional<String> validateUrlIsNotBlank(Optional<String> url) {
        if (url.get().isBlank()) {
            return Optional.empty();
        }
        return url;
    }


    private String upload(String image) throws IOException {

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

        validateStatusCodeIsSuccess(response.code());

        JSONObject jsonObject = new JSONObject(response.body().string());

        JSONObject jsonObjectData = jsonObject.getJSONObject("data");
        return jsonObjectData.getString("link");
    }

    private void validateStatusCodeIsSuccess(int statusCode) {
        if (statusCode != SUCCESS_STATUS_CODE) {
            throw new UnsupportedOperationException(URL_IS_NOT_VALID_ERROR_MESSAGE);
        }
    }
}
