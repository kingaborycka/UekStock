package pl.kiiniab.stock.sales.offerting;

import pl.kiiniab.stock.sales.basket.Basket;
import pl.kiiniab.stock.sales.basket.BasketItem;
import pl.kiiniab.stock.sales.catalog.ImageDetailsProvider;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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

        List<Integer> freeImagesIndexes = IntStream.range(1, lines.size() + 1)
                .filter(n -> n % 5 == 0)
                .boxed()
                .map(i -> lines.get(i-1).setForFree())
                .collect(Collectors.toList());

        BigDecimal offerTotal = lines
                .stream()
                .map(offerLine -> offerLine.getTotal())
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO);

        return Offer.of(checkDiscount(offerTotal), lines);
    }

    private BigDecimal checkDiscount(BigDecimal offerTotal) {
        if(offerTotal.compareTo(BigDecimal.valueOf(100.00)) > 0){
            offerTotal = offerTotal.multiply(BigDecimal.valueOf(0.9));
        }
        return offerTotal;
    }

    private OfferLine createOfferLine(BasketItem basketItem) {
        return new OfferLine(basketItem.getImageId(), basketItem.getQuantity(), imageDetailsProvider.getImageDetails(basketItem.getImageId()).getPrice());
    }
}