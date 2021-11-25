package pl.kiiniab.stock;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import pl.kiiniab.stock.productcatalogue.ImagesStorage;
import pl.kiiniab.stock.productcatalogue.ProductCatalogueFacade;

@SpringBootApplication
public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    @Bean
    public ProductCatalogueFacade productCatalogueFacade(ImagesStorage imagesStorage) {
        return new ProductCatalogueFacade();
    }

    @Bean
    public ImagesStorage imagesStorage() {
        return new ImagesStorage();
    }
}
