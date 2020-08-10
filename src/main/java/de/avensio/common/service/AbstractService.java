package de.avensio.common.service;

import de.avensio.common.persistence.model.INameableEntity;
import org.springframework.security.access.prepost.PreAuthorize;

public abstract class AbstractService<T extends INameableEntity> extends AbstractRawService<T> implements IService<T> {

    // Use this on business logic (service methods) to secure the access by a role
    // @PreAuthorize("hasRole('ROLE_ADMIN')")


    public AbstractService() {
        super();
    }

    // API

}
