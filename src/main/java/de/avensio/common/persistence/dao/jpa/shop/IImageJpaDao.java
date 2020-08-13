package de.avensio.common.persistence.dao.jpa.shop;

import de.avensio.common.persistence.IByNameApi;
import de.avensio.common.persistence.model.shop.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface IImageJpaDao extends JpaRepository<Image, Long>, JpaSpecificationExecutor<Image>, IByNameApi<Image> {
}
