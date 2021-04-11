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

@Service
public class ImgurServiceImpl implements ImgurService {

    private static final String IMGUR_IMAGE_UPLOAD_URL = "https://api.imgur.com/3/image";
    private static final String IMGUR_CLIENT_ID = "Client-ID 442f5d37036bc37";
    private static final String IMGUR_AUTHORIZATION = "Authorization";
    private static final String URL_IS_NOT_VALID_ERROR_MESSAGE = "Url is not valid";
    private static final String INITIAL_PROFILE_IMAGE = "https://i.imgur.com/GdDsxXO.png";


    public ImgurServiceImpl() {
    }

    public String uploadImageToImgurAndReturnUrl(Optional<MultipartFile> file,Optional<String> url) throws IOException {


        if (file.isPresent() && url.isPresent() ){
            throw new UnsupportedOperationException("Only local file or url");
        }else if (file.isEmpty() && url.isEmpty()){
           return INITIAL_PROFILE_IMAGE;
        }

        String image = "";
        if (file.isPresent()){
            image = Base64.getEncoder().encodeToString(file.get().getBytes());
        }else {

            URL testUrl = new URL(url.get());
            HttpURLConnection huc = (HttpURLConnection) testUrl.openConnection();

            int responseCode = huc.getResponseCode();

            if (responseCode != 200){
                throw new UnsupportedOperationException(URL_IS_NOT_VALID_ERROR_MESSAGE);
            }

            image = url.get();

        }


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

}
