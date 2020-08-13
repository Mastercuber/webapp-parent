package de.avensio.common.service.shop.impl;

import de.avensio.common.persistence.ServicePreconditions;
import de.avensio.common.persistence.dao.jpa.shop.IProductJpaDao;
import de.avensio.common.persistence.model.shop.Product;
import de.avensio.common.service.AbstractService;
import de.avensio.common.service.shop.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class ProductServiceImpl extends AbstractService<Product> implements IProductService {
    @Autowired
    private IProductJpaDao dao;

    public ProductServiceImpl() {
        super();
    }

    @Override
    public List<Product> findProductsByGenre(String genre) {
        return getDao().findByGenre(genre);
    }

    @Override
    public List<Product> findProductsByIds(List<Long> ids) {
        List<Product> products = new ArrayList<>();

        for (Long id : ids) {
            Product product = getDao().findById(id).get();
            ServicePreconditions.checkEntityExists(product);
            products.add(product);
        }

        return products;
    }

    @Override
    public List<Product> findProductsByName(String searchTerm) {
        return getDao().findAllByNameContains(searchTerm);
    }

    @Override
    public Product findByName(String name) {
        return getDao().findByName(name);
    }

    @Override
    protected IProductJpaDao getDao() {
        return dao;
    }

    @Override
    protected JpaSpecificationExecutor<Product> getSpecificationExecutor() {
        return dao;
    }
}
