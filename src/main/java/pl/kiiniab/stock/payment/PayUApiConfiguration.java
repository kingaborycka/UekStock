package pl.kiiniab.stock.payment;

import lombok.Data;

@Data
public class PayUApiConfiguration {
    String merchantPosId;
    String apiUrl;

    public PayUApiConfiguration(String merchantPosId, String apiUrl) {
        this.merchantPosId = merchantPosId;
        this.apiUrl = apiUrl;
    }
}
