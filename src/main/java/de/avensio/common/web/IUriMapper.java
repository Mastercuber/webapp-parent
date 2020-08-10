package de.avensio.common.web;

import de.avensio.common.persistence.model.IEntity;

public interface IUriMapper {

    <T extends IEntity> String getUriBase(final Class<T> clazz);

}
