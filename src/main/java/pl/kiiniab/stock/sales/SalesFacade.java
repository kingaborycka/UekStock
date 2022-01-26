package pl.kiiniab.stock.sales;

import pl.kiiniab.stock.sales.payment.PaymentGateway;
import pl.kiiniab.stock.sales.offerting.Offer;
import pl.kiiniab.stock.sales.offerting.OfferMaker;
import pl.kiiniab.stock.sales.ordering.InMemoryReservationStorage;
import pl.kiiniab.stock.sales.ordering.Reservation;
import pl.kiiniab.stock.sales.ordering.ReservationDetails;

public class SalesFacade {
    private BasketStorage basketStorage;
    private ImageDetailsProvider imageDetailsProvider;
    private OfferMaker offerMaker;
    private InMemoryReservationStorage reservationStorage;
    private PaymentGateway paymentGateway;

    public SalesFacade(
            BasketStorage basketStorage,
            ImageDetailsProvider imageDetailsProvider,
            OfferMaker offerMaker,
            InMemoryReservationStorage reservationStorage,
            PaymentGateway paymentGateway) {
        this.basketStorage = basketStorage;
        this.imageDetailsProvider = imageDetailsProvider;
        this.offerMaker = offerMaker;
        this.reservationStorage = reservationStorage;
        this.paymentGateway = paymentGateway;
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
        Basket basket = loadBasketForCustomer(customerId);
        Offer currentOffer = offerMaker.makeAnOffer(basket);

        Reservation reservation = Reservation.of(currentOffer, basket.getBasketItems(), customerData);
        reservation.registerPayment(paymentGateway);

        reservationStorage.save(reservation);

        return ReservationDetails.ofPayment(
                reservation.getId(),
                reservation.paymentDetails().getPaymentId(),
                reservation.paymentDetails().getPaymentUrl()
        );
    }
}