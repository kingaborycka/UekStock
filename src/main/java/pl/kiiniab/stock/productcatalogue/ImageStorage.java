package pl.kiiniab.stock.productcatalogue;

import java.util.HashMap;
import java.util.Optional;

public class ImageStorage {
    HashMap<String, Image> images;

    public ImageStorage() {
        this.images = new HashMap<>();
    }

    public void save(Image image){
        this.images.put(image.getImageId(),image);
    }

    public Optional<Image> load(String imageId) {
        return Optional.ofNullable(images.get(imageId));
    }
}
