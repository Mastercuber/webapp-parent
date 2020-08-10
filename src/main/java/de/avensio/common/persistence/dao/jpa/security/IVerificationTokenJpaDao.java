package de.avensio.common.persistence.dao.jpa.security;

import de.avensio.common.persistence.model.security.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IVerificationTokenJpaDao extends JpaRepository<VerificationToken, Long> {

    VerificationToken findByToken(String token);

}
