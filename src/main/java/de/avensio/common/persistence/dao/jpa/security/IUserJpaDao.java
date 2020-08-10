package de.avensio.common.persistence.dao.jpa.security;

import de.avensio.common.persistence.model.security.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUserJpaDao extends JpaRepository<User, Long> {

    User findByEmail(String email);

}
