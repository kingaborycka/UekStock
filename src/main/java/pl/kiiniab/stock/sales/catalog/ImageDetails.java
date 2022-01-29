package pl.kiiniab.stock.sales.catalog;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class ImageDetails {
    private String id;
    private BigDecimal price;
}