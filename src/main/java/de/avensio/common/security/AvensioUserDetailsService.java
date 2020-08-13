package de.avensio.common.security;

import de.avensio.common.persistence.dao.jpa.security.IPrincipalJpaDao;
import de.avensio.common.persistence.dao.jpa.security.IRoleJpaDao;
import de.avensio.common.persistence.dao.jpa.security.IUserJpaDao;
import de.avensio.common.persistence.model.security.Principal;
import de.avensio.common.persistence.model.security.Role;
import de.avensio.common.persistence.model.security.User;
import de.avensio.common.util.Um;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Service("userDetailsService")
@Transactional
public class AvensioUserDetailsService implements UserDetailsService {

    @Autowired
    private IUserJpaDao userJpaDao;
    @Autowired
    private IRoleJpaDao roleJpaDao;
    @Autowired
    private IPrincipalJpaDao principalJpaDao;

    @Override
    public UserDetails loadUserByUsername(final String email) throws UsernameNotFoundException {
        final User user = userJpaDao.findByEmail(email);
        final Principal principal = principalJpaDao.findByEmail(email);
        org.springframework.security.core.userdetails.User resultUser = null;
        List<Role> allRoles = roleJpaDao.findAll();

        if (user == null && principal == null) {
            throw new UsernameNotFoundException("No user found with username: " + email);
        }

        if (user != null) {
            resultUser = new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), user.getEnabled(), true, true, true, getAuthorities(Um.Roles.ROLE_USER));
        }
        if (principal != null) {
            resultUser = new org.springframework.security.core.userdetails.User(principal.getEmail(), principal.getPassword(), principal.isEnabled(), true, true, true, getAuthorities(principal.getRoles()));
        }

        return resultUser;
    }

    private Collection<? extends GrantedAuthority> getAuthorities(String role) {
        return Arrays.asList(new SimpleGrantedAuthority(role));
    }

    private Collection<? extends GrantedAuthority> getAuthorities(Collection<Role> roles) {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        for (Role role: roles) {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        }
        return authorities;
    }

}
