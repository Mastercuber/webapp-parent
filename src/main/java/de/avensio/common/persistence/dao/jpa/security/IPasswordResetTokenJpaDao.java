package de.avensio.common.persistence.dao.jpa.security;

import de.avensio.common.persistence.model.security.PasswordResetToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IPasswordResetTokenJpaDao extends JpaRepository<PasswordResetToken, Long> {

    PasswordResetToken findByToken(String token);

}
