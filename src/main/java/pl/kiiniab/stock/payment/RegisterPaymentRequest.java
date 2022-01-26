package pl.kiiniab.stock.payment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.kiiniab.stock.productcatalogue.Image;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class RegisterPaymentRequest {

    private String notifyUrl;
    private String customerIp;
    private String description;
    private String currencyCode;
    private Integer totalAmount;
    private String extOrderId;
    private Buyer buyer;
    private List<Product> products;
    private String merchantPosId;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Buyer {
        private String email;
        private String firstName;
        private String lastName;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Product {
        private String name;
        private Integer unitPrice;
        private Integer quantity;
    }
}
