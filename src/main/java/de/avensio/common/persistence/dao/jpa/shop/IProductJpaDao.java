package de.avensio.common.persistence.dao.jpa.shop;

import de.avensio.common.persistence.IByNameApi;
import de.avensio.common.persistence.model.shop.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IProductJpaDao extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product>, IByNameApi<Product> {

    List<Product> findByGenre(String genre);
    List<Product> findAllByNameContains(String searchTerm);
}
