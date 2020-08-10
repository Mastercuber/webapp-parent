package de.avensio.common.service.security.impl;

import de.avensio.common.persistence.dao.jpa.security.IPasswordResetTokenJpaDao;
import de.avensio.common.persistence.dao.jpa.security.IUserJpaDao;
import de.avensio.common.persistence.dao.jpa.security.IVerificationTokenJpaDao;
import de.avensio.common.persistence.model.security.PasswordResetToken;
import de.avensio.common.persistence.model.security.User;
import de.avensio.common.persistence.model.security.VerificationToken;
import de.avensio.common.service.security.IUserService;
import de.avensio.common.validation.EmailExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
class UserServiceImpl implements IUserService {

    @Autowired
    private IUserJpaDao userJpaDao;

    @Autowired
    private IVerificationTokenJpaDao verificationTokenJpaDao;

    @Autowired
    private IPasswordResetTokenJpaDao passwordTokenRepository;

    @Override
    public User registerNewUser(final User user) throws EmailExistsException {
        if (emailExist(user.getEmail())) {
            throw new EmailExistsException("There is an account with that email address: " + user.getEmail());
        }
        user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()));
        return userJpaDao.save(user);
    }

    @Override
    public User findUserByEmail(final String email) {
        return userJpaDao.findByEmail(email);
    }

    @Override
    public void createPasswordResetTokenForUser(final User user, final String token) {
        final PasswordResetToken myToken = new PasswordResetToken(token, user);
        passwordTokenRepository.save(myToken);
    }

    @Override
    public PasswordResetToken getPasswordResetToken(final String token) {
        return passwordTokenRepository.findByToken(token);
    }

    @Override
    public void changeUserPassword(final User user, final String password) {
        user.setPassword(BCrypt.hashpw(password, BCrypt.gensalt()));
        userJpaDao.save(user);
    }

    @Override
    public void createVerificationTokenForUser(final User user, final String token) {
        final VerificationToken myToken = new VerificationToken(token, user);
        verificationTokenJpaDao.save(myToken);
    }

    @Override
    public VerificationToken getVerificationToken(final String token) {
        return verificationTokenJpaDao.findByToken(token);
    }

    @Override
    public void saveRegisteredUser(final User user) {
        userJpaDao.save(user);
    }

    private boolean emailExist(final String email) {
        final User user = userJpaDao.findByEmail(email);
        return user != null;
    }

}
