package de.avensio.common.service;

import de.avensio.common.persistence.IByNameApi;
import de.avensio.common.persistence.model.INameableEntity;

public interface IService<T extends INameableEntity> extends IRawService<T>, IByNameApi<T> {

    //

}
