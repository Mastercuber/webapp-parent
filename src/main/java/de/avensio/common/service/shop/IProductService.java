package de.avensio.common.service.shop;

import de.avensio.common.persistence.model.shop.Product;
import de.avensio.common.service.IService;

import java.util.List;

public interface IProductService extends IService<Product> {

    List<Product> findProductsByGenre(String genre);

    List<Product> findProductsByIds(List<Long> ids);

}
