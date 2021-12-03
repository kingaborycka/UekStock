package pl.kiiniab.stock.productcatalogue;

import org.springframework.data.repository.CrudRepository;

public interface CrudImageStorage extends CrudRepository<Image, String> {
}
