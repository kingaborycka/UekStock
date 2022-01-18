package pl.kiiniab.stock.sales;
import java.math.BigDecimal;

public class BasketItem {
    private String imageId;
    private BigDecimal price;

    public BasketItem(String imageId, BigDecimal price) {
        this.imageId = imageId;
        this.price = price;
    }

    static BasketItem of(String imageId, BigDecimal price) {
        return new BasketItem(imageId, price);
    }

    public String getImageId() {
        return imageId;
    }

    public int getQuantity() {
        return 1;
    }

}
