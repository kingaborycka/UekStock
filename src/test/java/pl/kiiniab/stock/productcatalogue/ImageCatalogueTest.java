package pl.kiiniab.stock.productcatalogue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ImageCatalogueTest {

    @Autowired
    TestRestTemplate restTemplate;

    @LocalServerPort
    int serverPort;

    @Autowired
    ImageCatalogue imageCatalogue;

    @BeforeEach
    void clearImage() {
        imageCatalogue.empty();
    }

    @Test
    void itLoadsProductsViaEndpoint() {
        // given
        thereIsDraftImage("example 0");
        thereIsImage("example 1");
        thereIsImage("example 2");

        // when
        ResponseEntity<Image[]> response = callApiForProducts();
        Image[] images = response.getBody();

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, images.length);
    }

    private void thereIsImage(String imageName) {
        String id = imageCatalogue.addImage(
                imageName,
                BigDecimal.ONE,
                Arrays.asList("tag1"),
                "image path"
        );

        imageCatalogue.publish(id);
    }

    private void thereIsDraftImage(String imageName) {
        imageCatalogue.addImage(
                imageName,
                BigDecimal.ONE,
                Arrays.asList("tag1"),
                "image path"
        );
    }

    private ResponseEntity<Image[]> callApiForProducts() {
        String url = String.format(
                "http://localhost:%s/api/images",
                serverPort);

        ResponseEntity<Image[]> response =
                restTemplate.getForEntity(url, Image[].class);
        return response;
    }
}