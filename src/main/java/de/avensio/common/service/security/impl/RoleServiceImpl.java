package de.avensio.common.service.security.impl;

import de.avensio.common.persistence.dao.jpa.security.IRoleJpaDao;
import de.avensio.common.persistence.model.security.Role;
import de.avensio.common.service.AbstractService;
import de.avensio.common.service.security.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class RoleServiceImpl extends AbstractService<Role> implements IRoleService {

    @Autowired
    private IRoleJpaDao dao;

    public RoleServiceImpl() {
        super();
    }

    // API
    // get/find
    @Override
    public Role findByName(final String name) {
        return getDao().findByName(name);
    }

    // create
    @Override
    public Role create(final Role entity) { return super.create(entity); }

    // Spring
    @Override
    protected final IRoleJpaDao getDao() {
        return dao;
    }

    @Override
    protected JpaSpecificationExecutor<Role> getSpecificationExecutor() {
        return dao;
    }

}
