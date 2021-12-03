package pl.kiiniab.stock.productcatalogue;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;


@Entity
public class Image {
    @Id
    private final String imageId;
    private final String title;
    private final BigDecimal price;
    @Transient
    private final List<String> keyWords;
    private final String filePath;

    public Image(UUID imageId, String title, BigDecimal price, List<String> keyWords, String filePath) {
        this.imageId = imageId.toString();
        this.title = title;
        this.price = price;
        this.keyWords = keyWords;
        this.filePath = filePath;
    }

    public String getImageId() {
        return imageId;
    }

    public String getTitle() {
        return title;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public List<String> getKeyWords() { return keyWords; }

    public String getFilePath() {
        return filePath;
    }

}
