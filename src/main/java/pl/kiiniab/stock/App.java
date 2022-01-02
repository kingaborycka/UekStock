package pl.kiiniab.stock;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import pl.kiiniab.stock.productcatalogue.ImageCatalogue;
import pl.kiiniab.stock.productcatalogue.ImageRepository;

import java.math.BigDecimal;
import java.util.Arrays;

@SpringBootApplication
public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    @Bean
    public ImageCatalogue createImageCatalogue(ImageRepository imageRepository) {
        ImageCatalogue imageCatalogue = new ImageCatalogue(imageRepository);
        String imageId1 = imageCatalogue.addImage(
                "Image 1 - example",
                BigDecimal.valueOf(100.50),
                Arrays.asList("tag1", "tag2"),
                "filePath"
        );
        imageCatalogue.publish(imageId1);

        String imageId2 = imageCatalogue.addImage(
                "Image 2 - example",
                BigDecimal.valueOf(100.50),
                Arrays.asList("tag1", "tag2", "tag3"),
                "filePath"
        );
        imageCatalogue.publish(imageId2);

        return imageCatalogue;
    }
}
