package de.avensio.common.persistence.dao.jpa.security;

import de.avensio.common.persistence.model.security.SecurityQuestion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ISecurityQuestionJpaDao extends JpaRepository<SecurityQuestion, Long> {

    SecurityQuestion findByQuestionDefinitionIdAndUserIdAndAnswer(Long questionDefinitionId, Long userId, String answer);

}
