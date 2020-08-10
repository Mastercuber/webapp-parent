package de.avensio.common.service.security;

import de.avensio.common.persistence.model.security.PasswordResetToken;
import de.avensio.common.persistence.model.security.User;
import de.avensio.common.persistence.model.security.VerificationToken;
import de.avensio.common.validation.EmailExistsException;

public interface IUserService {

    User registerNewUser(User user) throws EmailExistsException;

    User findUserByEmail(String email);

    void createPasswordResetTokenForUser(User user, String token);

    PasswordResetToken getPasswordResetToken(String token);

    void changeUserPassword(User user, String password);

    void createVerificationTokenForUser(User user, String token);

    VerificationToken getVerificationToken(String token);

    void saveRegisteredUser(User user);

}
