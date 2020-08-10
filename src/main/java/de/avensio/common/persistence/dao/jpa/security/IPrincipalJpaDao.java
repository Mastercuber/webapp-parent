package de.avensio.common.persistence.dao.jpa.security;

import de.avensio.common.persistence.IByNameApi;
import de.avensio.common.persistence.model.security.Principal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface IPrincipalJpaDao extends JpaRepository<Principal, Long>, JpaSpecificationExecutor<Principal>, IByNameApi<Principal> {
    Principal findByUsername(String username);

    Principal findByEmail(String email);
}
