package pl.kiiniab.stock.sales;

import org.springframework.web.bind.annotation.*;
import pl.kiiniab.stock.sales.offerting.Offer;
import pl.kiiniab.stock.sales.ordering.ReservationDetails;

import javax.servlet.http.HttpSession;
import java.util.UUID;

@RestController
public class SalesController {

    public static final String CURRENT_CUSTOMER_ID_KEY = "CUSTOMER_ID";
    private HttpSession httpSession;
    SalesFacade sales;

    public SalesController(SalesFacade salesFacade, HttpSession httpSession) {

        this.sales = salesFacade;
        this.httpSession = httpSession;
    }

    @PostMapping("/api/add-image/{imageId}")
    public void addImageToBasket(@PathVariable String imageId) {
        sales.addToBasket(getCurrentCustomerId(), imageId);
    }

    @GetMapping("/api/current-offer")
    public Offer currentOffer() {
        return sales.getCurrentOffer(getCurrentCustomerId());
    }

    @PostMapping("/api/accept-offer")
    public ReservationDetails acceptOffer(@RequestBody CustomerData customerData) {
        return sales.acceptOffer(getCurrentCustomerId(), customerData);
    }

    @GetMapping("/api/current-customer-id")
    public String currentCustomerId() {
        return getCurrentCustomerId();
    }

    private String getCurrentCustomerId() {
        Object currentCustomerId = httpSession.getAttribute(CURRENT_CUSTOMER_ID_KEY);

        if (currentCustomerId != null) {
            return (String) currentCustomerId;
        }

        String newCustomerId = UUID.randomUUID().toString();
        httpSession.setAttribute(CURRENT_CUSTOMER_ID_KEY, newCustomerId);

        return newCustomerId;
    }
}
