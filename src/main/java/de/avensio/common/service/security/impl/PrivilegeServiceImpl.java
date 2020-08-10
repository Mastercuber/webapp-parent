package de.avensio.common.service.security.impl;

import de.avensio.common.persistence.dao.jpa.security.IPrivilegeJpaDao;
import de.avensio.common.persistence.model.security.Privilege;
import de.avensio.common.service.AbstractService;
import de.avensio.common.service.security.IPrivilegeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PrivilegeServiceImpl extends AbstractService<Privilege> implements IPrivilegeService {

    @Autowired
    private IPrivilegeJpaDao dao;

    public PrivilegeServiceImpl() {
        super();
    }

    // API
    // find
    @Override
    public Privilege findByName(final String name) {
        return getDao().findByName(name);
    }

    // Spring
    @Override
    protected final IPrivilegeJpaDao getDao() {
        return dao;
    }

    @Override
    protected JpaSpecificationExecutor<Privilege> getSpecificationExecutor() {
        return dao;
    }

}
