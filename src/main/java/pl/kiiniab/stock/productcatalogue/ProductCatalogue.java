package pl.kiiniab.stock.productcatalogue;

import org.springframework.data.repository.CrudRepository;

public interface ProductCatalogue extends CrudRepository<Image, String> {
}
