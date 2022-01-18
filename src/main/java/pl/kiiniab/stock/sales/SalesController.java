package pl.kiiniab.stock.sales;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.kiiniab.stock.sales.offerting.Offer;

@RestController
public class SalesController {

    public static final String CURRENT_CUSTOMER_ID = "Kinga";

    SalesFacade sales;

    public SalesController(SalesFacade salesFacade) {
        this.sales = salesFacade;
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
    public void acceptOffer(@PathVariable String imageId, CustomerData customerData) {
        sales.acceptOffer(getCurrentCustomerId(), customerData);
    }

    private String getCurrentCustomerId() {
        return CURRENT_CUSTOMER_ID;
    }

}
