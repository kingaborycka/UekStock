package pl.kiiniab.stock.sales.payment;

import pl.kiiniab.stock.sales.ordering.CustomerDetails;
import pl.kiiniab.stock.sales.ordering.PaymentDetails;

import java.math.BigDecimal;

public interface PaymentGateway {
    PaymentDetails register(String id, BigDecimal total, CustomerDetails customerDetails);
}
