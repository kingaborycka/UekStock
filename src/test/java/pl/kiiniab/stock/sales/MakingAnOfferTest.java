package pl.kiiniab.stock.sales;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.kiiniab.stock.sales.basket.BasketStorage;
import pl.kiiniab.stock.sales.catalog.ImageDetails;
import pl.kiiniab.stock.sales.payment.DummyPaymentGateway;
import pl.kiiniab.stock.sales.offerting.Offer;
import pl.kiiniab.stock.sales.offerting.OfferMaker;
import pl.kiiniab.stock.sales.ordering.InMemoryReservationStorage;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.util.UUID;

public class MakingAnOfferTest {

    private BasketStorage basketStorage;
    private DummyImageDetailsProvider imageDetailsProvider;

    @BeforeEach
    void initializeSharedObjects() {
        basketStorage = new BasketStorage();
        imageDetailsProvider = new DummyImageDetailsProvider();
    }

    @Test
    void itGenerateOfferBasedOnImagesWithinBasket() {
        // given
        String imageId = thereIsImage("image-1", BigDecimal.valueOf(10.10));
        String customerId = thereIsCustomer();
        SalesFacade sales = thereIsSalesModule();

        // when
        sales.addToBasket(customerId, imageId);
        sales.addToBasket(customerId, imageId);
        Offer currentOffer = sales.getCurrentOffer(customerId);

        // then
        assertEquals(BigDecimal.valueOf(20.20), currentOffer.getTotal());
        assertEquals(2, currentOffer.getLinesCount());
    }

    private String thereIsImage(String imageId, BigDecimal price) {
        ImageDetails image = new ImageDetails(imageId, price);
        imageDetailsProvider.images.put(imageId, image);
        return imageId;
    }

    private String thereIsCustomer() {
        return UUID.randomUUID().toString();
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
}