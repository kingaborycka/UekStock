package pl.kiiniab.stock.productcatalogue;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ImageCatalogueController {
    private ImageCatalogue imageCatalogue;

    public ImageCatalogueController(ImageCatalogue imageCatalogue) {
        this.imageCatalogue = imageCatalogue;
    }

    @GetMapping("/api/images")
    List<Image> allImages() {
        return imageCatalogue.allImages();
    }
}
