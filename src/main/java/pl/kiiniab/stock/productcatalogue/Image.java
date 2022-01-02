package pl.kiiniab.stock.productcatalogue;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Image {
    @Id
    private String imageId;
    private String title;
    private BigDecimal price;
    @Transient
    private List<String> keyWords;
    private String filePath;
    private boolean published = false;

    public Image(UUID imageId, String title, BigDecimal price, List<String> keyWords, String filePath) {
        this.imageId = imageId.toString();
        this.title = title;
        this.price = price;
        this.keyWords = keyWords;
        this.filePath = filePath;
        this.published = false;
    }

    public void publish() {
        this.published = true;
    }

}
