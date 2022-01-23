package pl.kiiniab.stock.sales;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.kiiniab.stock.sales.offerting.OfferMaker;
import pl.kiiniab.stock.sales.ordering.InMemoryReservationStorage;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CollectingImagesTest {
    private BasketStorage basketStorage;
    private DummyImageDetailsProvider imageDetailsProvider;

    @BeforeEach
    void initializeSharedObjects() {
        basketStorage = new BasketStorage();
        imageDetailsProvider = new DummyImageDetailsProvider();
    }

    @Test
    void itAllowsAddingImagesToBasket() {
        // given
        String imageId = thereIsImage("image-1");
        String customerId = thereIsCustomer("Kinga");
        SalesFacade sales = thereIsSalesModule();

        // When
        sales.addToBasket(customerId, imageId);

        // Then
        thereIsXImagesInCustomersBasket(1, customerId);
    }

    @Test
    void itAllowsToAddMultipleImagesToBasket() {
        // Given
        String imageId1 = thereIsImage("image-1");
        String imageId2 = thereIsImage("image-2");
        String customerId = thereIsCustomer("Kinga");
        SalesFacade sales = thereIsSalesModule();

        // When
        sales.addToBasket(customerId, imageId1);
        sales.addToBasket(customerId, imageId2);

        // Then
        thereIsXImagesInCustomersBasket(2, customerId);
    }

    @Test
    void itDoNotShareCustomersBaskets() {
        // Given
        String imageId1 = thereIsImage("image-1");
        String customerId1 = thereIsCustomer("Kinga");
        String customerId2 = thereIsCustomer("Ania");
        SalesFacade sales = thereIsSalesModule();

        // When
        sales.addToBasket(customerId1, imageId1);
        sales.addToBasket(customerId2, imageId1);

        // Then
        thereIsXImagesInCustomersBasket(1, customerId1);
        thereIsXImagesInCustomersBasket(1, customerId2);
    }

    @Test
    void itAllowToAddSameImageMultpipleTime() {
        // given
        String imageId = thereIsImage("image-1");
        String customerId = thereIsCustomer("Kinga");
        SalesFacade sales = thereIsSalesModule();

        // when
        sales.addToBasket(customerId, imageId);
        sales.addToBasket(customerId, imageId);
        sales.addToBasket(customerId, imageId);

        // then
        thereIsXImagesInCustomersBasket(3, customerId);
        thereIsXQuantityOfImageXInCustomerBasket(3, imageId, customerId);
    }

    private void thereIsXQuantityOfImageXInCustomerBasket(int i, String imageId, String customerId) {

    }


    private SalesFacade thereIsSalesModule() {
        return new SalesFacade(
                basketStorage,
                imageDetailsProvider,
                new OfferMaker(imageDetailsProvider),
                new InMemoryReservationStorage(),
                new DummyPaymentGateway()
        );
    }

    private String thereIsCustomer(String customerName) {
        return customerName;
    }

    private String thereIsImage(String imageId) {
       ImageDetails image = new ImageDetails(imageId, BigDecimal.TEN);
        imageDetailsProvider.images.put(imageId, image);
        return imageId;
    }

    private void thereIsXImagesInCustomersBasket(int imagesCount, String customerId) {
        Basket basket = loadBasketForCustomer(customerId);
        assertEquals(imagesCount, basket.getImagesCount());
    }

    private Basket loadBasketForCustomer(String customerId) {
        return basketStorage.getForCustomer(customerId).get();
    }
}

class DummyImageDetailsProvider implements ImageDetailsProvider {
    Map<String, ImageDetails> images;

    public DummyImageDetailsProvider() {
        this.images = new HashMap<>();
    }

    @Override
    public ImageDetails getImageDetails(String imageId) {
        return images.get(imageId);
    }
}