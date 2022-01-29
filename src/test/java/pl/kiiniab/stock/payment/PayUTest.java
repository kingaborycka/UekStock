package pl.kiiniab.stock.payment;


import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class PayUTest {

    @Test
    void itAllowsRegisterPaymentViaPayUClient() {
        RegisterPaymentRequest request = thereIsExamplePaymentRequest();
        PayU payU = thereIsPayUClient();

        RegisterPaymentResponse response = payU.handle(request);

        assertTrue(response.isSuccess());
        assertNotNull(response.getOrderId());
        assertNotNull(response.getRedirectUri());

    }

    private PayU thereIsPayUClient() {
        return new PayU(
                new RestTemplate(),
                new PayUApiConfiguration("300746", "https://secure.snd.payu.com"));
    }

    private RegisterPaymentRequest thereIsExamplePaymentRequest() {
        return RegisterPaymentRequest.builder()
                .notifyUrl("https://your.eshop.com/notify")
                .customerIp("127.0.0.1")
                .description("RTV market")
                .currencyCode("PLN")
                .totalAmount(BigDecimal.valueOf(100.50).multiply(BigDecimal.valueOf(100)).intValue())
                .extOrderId(UUID.randomUUID().toString())
                .buyer(new RegisterPaymentRequest.Buyer("john.doe@example.com", "john", "doe"))
                .products(Arrays.asList(
                        new RegisterPaymentRequest.Product("image 1", BigDecimal.valueOf(20.50).intValue(), 1),
                        new RegisterPaymentRequest.Product("image 2", BigDecimal.valueOf(100.50).intValue(), 1)
                ))
                .build();
    }

}