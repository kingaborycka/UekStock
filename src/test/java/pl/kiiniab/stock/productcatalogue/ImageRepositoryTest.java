package pl.kiiniab.stock.productcatalogue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.UUID;

@SpringBootTest
public class ImageRepositoryTest {
    @Autowired
    ImageRepository imageRepository;

    @Test
    void itStoreImage() {
        UUID id = UUID.randomUUID();
        Image image = new Image(
                id,
                "My prod",
                BigDecimal.ONE,
                Arrays.asList("k1", "k2"),
                "media.jpeg");
        imageRepository.save(image);

        Image loaded = imageRepository.findById(id.toString()).get();
    }
}
