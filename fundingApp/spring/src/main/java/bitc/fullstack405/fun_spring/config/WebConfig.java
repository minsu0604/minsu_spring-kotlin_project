package bitc.fullstack405.fun_spring.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // "/image/**" 경로로 요청이 들어오면 "C://FullStack405/react/team1_project/spring/image/"에서 이미지를 찾기
        registry.addResourceHandler("/image/**")
                .addResourceLocations("C://FullStack405/react/team1_project/spring/image/");
    }
}