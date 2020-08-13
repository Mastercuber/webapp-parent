package de.avensio.common.service.shop.impl;

import de.avensio.common.persistence.dao.jpa.shop.IImageJpaDao;
import de.avensio.common.persistence.dao.jpa.shop.IProductJpaDao;
import de.avensio.common.persistence.model.shop.Image;
import de.avensio.common.persistence.model.shop.Product;
import de.avensio.common.service.AbstractService;
import de.avensio.common.service.shop.IImageService;
import de.avensio.common.service.shop.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ImageServiceImpl extends AbstractService<Image> implements IImageService {
    @Autowired
    private IImageJpaDao dao;

    public ImageServiceImpl() {
        super();
    }

    @Override
    public Image findByName(String name) {
        return getDao().findByName(name);
    }

    @Override
    protected IImageJpaDao getDao() {
        return dao;
    }

    @Override
    protected JpaSpecificationExecutor<Image> getSpecificationExecutor() {
        return dao;
    }
}
