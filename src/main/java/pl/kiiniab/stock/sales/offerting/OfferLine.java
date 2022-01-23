package pl.kiiniab.stock.sales.offerting;

import java.math.BigDecimal;

public class OfferLine {
    private final String imageId;
    private int quantity;
    private final BigDecimal price;

    public OfferLine(String imageId, int quantity, BigDecimal price) {

        this.imageId = imageId;
        this.quantity = quantity;
        this.price = price;
    }

    public BigDecimal getTotal() {
        return price.multiply(BigDecimal.valueOf(quantity));
    }

    public int setForFree() {
        this.quantity = 0;
        return quantity;
    }
}