package bitc.fullstack405.fun_spring.util;

import lombok.val;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ImageURL {

    public static String BASE_URL = "";

    @Value("${image-base-url}")
    public void setBaseUrl(String url){
        BASE_URL = url;
    }

    public ArrayList<String> bannerImages() {
        String img1 = BASE_URL + "bannerList/" + "1.jpg";

        String img2 = BASE_URL + "bannerList/" + "2.jpg";
        String img3 = BASE_URL + "bannerList/" + "3.jpg";
        return new ArrayList<>(List.of(img1, img2, img3));
    }

    public static String projectImg2(String imgName) {
        return BASE_URL +"projectList/" + imgName;
    }

    public static String projectImg(String content) {
        return BASE_URL + "contentList/" + content;
    }

}
