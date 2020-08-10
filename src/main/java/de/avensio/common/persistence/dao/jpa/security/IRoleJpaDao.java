package de.avensio.common.persistence.dao.jpa.security;

import de.avensio.common.persistence.IByNameApi;
import de.avensio.common.persistence.model.security.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface IRoleJpaDao extends JpaRepository<Role, Long>, JpaSpecificationExecutor<Role>, IByNameApi<Role> {
    //
}
