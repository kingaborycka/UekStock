package pl.kiiniab.stock.sales;

import pl.kiiniab.stock.sales.offerting.Offer;
import pl.kiiniab.stock.sales.offerting.OfferMaker;

public class SalesFacade {
    private BasketStorage basketStorage;
    private ImageDetailsProvider imageDetailsProvider;
    private OfferMaker offerMaker;


    public SalesFacade(BasketStorage basketStorage, ImageDetailsProvider imageDetailsProvider, OfferMaker offerMaker) {
        this.basketStorage = basketStorage;
        this.imageDetailsProvider = imageDetailsProvider;
        this.offerMaker = offerMaker;
    }

    public void addToBasket(String customerId, String imageId) {
        Basket basket = loadBasketForCustomer(customerId);
        ImageDetails image = imageDetailsProvider.getImageDetails(imageId);

        basket.add(BasketItem.of(image.getId(), image.getPrice()));

        basketStorage.save(customerId, basket);
    }

    private Basket loadBasketForCustomer(String customerId) {
        return basketStorage.getForCustomer(customerId)
                .orElse(Basket.empty());
    }
    public Offer getCurrentOffer(String customerId) {
        Basket basket = loadBasketForCustomer(customerId);
        return offerMaker.makeAnOffer(basket);
    }

    public ReservationDetails acceptOffer(String customerId, CustomerData customerData) {
        return ReservationDetails.ofPayment("reservationId", "paymentId", "paymentUrl");
    }
}