package pl.kiiniab.stock.sales.offerting;

import pl.kiiniab.stock.sales.Basket;
import pl.kiiniab.stock.sales.BasketItem;
import pl.kiiniab.stock.sales.ImageDetailsProvider;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

public class OfferMaker {
    private ImageDetailsProvider imageDetailsProvider;

    public OfferMaker(ImageDetailsProvider imageDetailsProvider) {
        this.imageDetailsProvider = imageDetailsProvider;
    }

    public Offer makeAnOffer(Basket basket) {
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
}