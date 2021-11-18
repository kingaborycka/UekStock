package pl.kiiniab.stock.productcatalogue;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class ProductCatalogueTest {

    @Test
    void itWorks() {
        assertEquals(1,1);
    }

    @Test
    void itAllowsAddPicture() {
        // given
        ProductCatalogueFacade api = thereIsProductCatalogue();

        // when
        String imageId = api.addPicture(
                                "Nice picture",
                                BigDecimal.valueOf(12.10),
                                Arrays.asList("#nice", "#picture"),
                                "images/picture.jpeg");

        // then
        thereIsProductWithIdAvailable(api, imageId);
    }

    private ProductCatalogueFacade thereIsProductCatalogue() {
        return new ProductCatalogueFacade();
    }

    private void thereIsProductWithIdAvailable(ProductCatalogueFacade api, String imageId) {
        assertTrue(api.isProductExists(imageId));
    }
}