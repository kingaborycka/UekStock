package pl.kiiniab.stock.productcatalogue;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ImagesStorage {
    Map<String, Image> images;

    public ImagesStorage() {
        this.images = new HashMap<>();
    }

    public void save(Image image){
        this.images.put(image.getImageId(),image);
    }

    public Optional<Image> load(String imageId) {
        return Optional.ofNullable(images.get(imageId));
    }
}
