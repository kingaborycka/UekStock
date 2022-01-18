package pl.kiiniab.stock.sales;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

public class SalesFacade {
    private BasketStorage basketStorage;
    private ImageDetailsProvider imageDetailsProvider;


    public SalesFacade(BasketStorage basketStorage, ImageDetailsProvider imageDetailsProvider) {
        this.basketStorage = basketStorage;
        this.imageDetailsProvider = imageDetailsProvider;
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

        List<OfferLine> lines = basket.getBasketItems()
                .stream()
                .map(this::createOfferLine)
                .collect(Collectors.toList());

        BigDecimal offerTotal = lines
                .stream()
                .map(offerLine -> offerLine.getTotal())
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO);

        return Offer.of(offerTotal, lines);
    }

    private OfferLine createOfferLine(BasketItem basketItem) {
        return new OfferLine(basketItem.getImageId(), basketItem.getQuantity(), imageDetailsProvider.getImageDetails(basketItem.getImageId()).getPrice());
    }

    public ReservationDetails acceptOffer(String customerId, CustomerData customerData) {
        return ReservationDetails.ofPayment("reservationId", "paymentId", "paymentUrl");
    }
}