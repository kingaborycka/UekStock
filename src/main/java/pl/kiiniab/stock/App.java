package pl.kiiniab.stock;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import pl.kiiniab.stock.payment.PayU;
import pl.kiiniab.stock.sales.payment.PayUPaymentGateway;
import pl.kiiniab.stock.productcatalogue.Image;
import pl.kiiniab.stock.productcatalogue.ImageCatalogue;
import pl.kiiniab.stock.productcatalogue.ImageRepository;
import pl.kiiniab.stock.sales.*;
import pl.kiiniab.stock.sales.offerting.OfferMaker;
import pl.kiiniab.stock.sales.ordering.InMemoryReservationStorage;
import pl.kiiniab.stock.sales.ordering.ReservationRepository;

import java.math.BigDecimal;
import java.util.Arrays;

@SpringBootApplication
public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    @Bean
    public ImageCatalogue createImageCatalogue(ImageRepository imageRepository) {
        ImageCatalogue imageCatalogue = new ImageCatalogue(imageRepository);
        String imageId1 = imageCatalogue.addImage(
                "Image 1 - example",
                BigDecimal.valueOf(20.50),
                Arrays.asList("tag1", "tag2"),
                "https://picsum.photos/300"
        );
        imageCatalogue.publish(imageId1);

        String imageId2 = imageCatalogue.addImage(
                "Image 2 - example",
                BigDecimal.valueOf(100.50),
                Arrays.asList("tag1", "tag2", "tag3"),
                "https://picsum.photos/200"
        );
        imageCatalogue.publish(imageId2);

        String imageId3 = imageCatalogue.addImage(
                "Image 3 - example",
                BigDecimal.valueOf(11.50),
                Arrays.asList("tag3", "tag2", "tag3"),
                "https://picsum.photos/350"
        );
        imageCatalogue.publish(imageId3);

        return imageCatalogue;
    }

    @Bean
    public SalesFacade createSalesFacade(ImageDetailsProvider imageDetailsProvider, PayU payU) {
        return new SalesFacade(
                new BasketStorage(),
                imageDetailsProvider,
                new OfferMaker(imageDetailsProvider),
                new InMemoryReservationStorage(),
                new PayUPaymentGateway(payU)
        );
    }

    @Bean
    public ImageDetailsProvider imageDetailsProvider(ImageCatalogue imageCatalogue ) {
        return (id) -> {
            Image image = imageCatalogue.getById(id);
            return new ImageDetails(
                    image.getImageId(),
                    image.getPrice()
            );
        };
    }

    @Bean
    public JpaReservationStorage createJpaReervationStorage(ReservationRepository reservationRepository) {
        return new JpaReservationStorage(reservationRepository);
    }
}
