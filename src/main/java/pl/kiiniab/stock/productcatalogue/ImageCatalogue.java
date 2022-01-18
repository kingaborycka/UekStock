package pl.kiiniab.stock.productcatalogue;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class ImageCatalogue {

    private ImageRepository repository;

    public ImageCatalogue(ImageRepository repository) {
        this.repository = repository;
    }

    public String addImage(String title, BigDecimal price, List<String> keyWords, String filePath) {
        Image image = new Image(UUID.randomUUID(),title, price, keyWords, filePath);
        repository.save(image);
        return image.getImageId();
    }

    public boolean isImageExists(String imageId) {
        return repository.existsById(imageId);
    }

    public void publish(String id) {
        Image loadedImage = repository.findById(id).get();
        loadedImage.publish();
        repository.save(loadedImage);
    }

    public List<Image> allImages() {
        return repository.findAll()
                .stream()
                .filter(Image::isPublished)
                .collect(Collectors.toList());
    }

    public void empty() {
        repository.deleteAll();
    }

    public Image getById(String imageId) {
        Image image = repository.findById(imageId).get();
        return image;
    }
}
