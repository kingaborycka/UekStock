package pl.kiiniab.stock;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.kiiniab.stock.productcatalogue.Image;
import pl.kiiniab.stock.productcatalogue.ProductCatalogue;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.UUID;

@SpringBootTest
public class JpaImageStorageTest {
    @Autowired
    ProductCatalogue productCatalogue;

    @Test
    void itStoreProduct() {
        UUID id = UUID.randomUUID();
        Image product = new Image(
                id,
                "My prod",
                BigDecimal.ONE,
                Arrays.asList("k1", "k2"),
                "media.jpeg");
        productCatalogue.save(product);

        Image loaded = productCatalogue.findById(id.toString()).get();
    }
}
