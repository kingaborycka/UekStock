package pl.kiiniab.stock.productcatalogue;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public class ProductCatalogueFacade {

    ImagesStorage imagesStorage;

    public ProductCatalogueFacade() {
        this.imagesStorage = new ImagesStorage();
    }

    public String addPicture(String title, BigDecimal price, List<String> keyWords, String filePath) {
        Image image = new Image(UUID.randomUUID(),title, price, keyWords, filePath);
        imagesStorage.save(image);
        return image.getImageId();
    }

    public boolean isProductExists(String imageId) {
        return imagesStorage.load(imageId).isPresent();
    }
}
