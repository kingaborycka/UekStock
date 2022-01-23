package pl.kiiniab.stock.sales.ordering;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class PaymentDetails {
    private String reservationId;
    private String paymentId;
    private String paymentUrl;

}