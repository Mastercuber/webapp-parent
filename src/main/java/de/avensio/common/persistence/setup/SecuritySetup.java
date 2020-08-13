package de.avensio.common.persistence.setup;

import com.google.common.base.Preconditions;
import com.google.common.collect.Sets;
import de.avensio.common.persistence.model.security.Principal;
import de.avensio.common.persistence.model.security.Privilege;
import de.avensio.common.persistence.model.security.Role;
import de.avensio.common.service.security.IPrincipalService;
import de.avensio.common.service.security.IPrivilegeService;
import de.avensio.common.service.security.IRoleService;
import de.avensio.common.spring.util.Profiles;
import de.avensio.common.util.Um;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Set;

/**
 * This simple setup class will run during the bootstrap process of Spring and will create some setup data <br>
 * The main focus here is creating some standard privileges, then roles and finally some default principals/users
 */
@Component
@Profile(value = { Profiles.DEVELOPMENT, Profiles.INTEGRATION_TEST })
public class SecuritySetup implements ApplicationListener<ContextRefreshedEvent> {
    private final Logger logger = LoggerFactory.getLogger(SecuritySetup.class);

    private boolean setupDone;

    @Autowired
    private IPrincipalService principalService;

    @Autowired
    private IRoleService roleService;

    @Autowired
    private IPrivilegeService privilegeService;

    public SecuritySetup() {
        super();
    }

    //

    /**
     * - note that this is a compromise - the flag makes this bean statefull which can (and will) be avoided in the future by a more advanced mechanism <br>
     * - the reason for this is that the context is refreshed more than once throughout the lifecycle of the deployable <br>
     * - alternatives: proper persisted versioning
     */
    @Override
    public final void onApplicationEvent(final ContextRefreshedEvent event) {
        if (!setupDone) {
            logger.info("Executing Security Setup");

            createPrivileges();
            createRoles();
            createPrincipals();

            setupDone = true;
            logger.info("Security Setup Done");
        }
    }

    // Privilege
    private void createPrivileges() {
        createPrivilegeIfNotExisting(Um.Privileges.CAN_PRIVILEGE_READ);
        createPrivilegeIfNotExisting(Um.Privileges.CAN_PRIVILEGE_WRITE);

        createPrivilegeIfNotExisting(Um.Privileges.CAN_ROLE_READ);
        createPrivilegeIfNotExisting(Um.Privileges.CAN_ROLE_WRITE);

    }

    final void createPrivilegeIfNotExisting(final String name) {
        final Privilege entityByName = privilegeService.findByName(name);
        if (entityByName == null) {
            final Privilege entity = new Privilege(name);
            privilegeService.create(entity);
        }
    }

    // Role
    private void createRoles() {
        final Privilege canPrivilegeRead = privilegeService.findByName(Um.Privileges.CAN_PRIVILEGE_READ);
        final Privilege canPrivilegeWrite = privilegeService.findByName(Um.Privileges.CAN_PRIVILEGE_WRITE);
        final Privilege canRoleRead = privilegeService.findByName(Um.Privileges.CAN_ROLE_READ);
        final Privilege canRoleWrite = privilegeService.findByName(Um.Privileges.CAN_ROLE_WRITE);

        Preconditions.checkNotNull(canPrivilegeRead);
        Preconditions.checkNotNull(canPrivilegeWrite);
        Preconditions.checkNotNull(canRoleRead);
        Preconditions.checkNotNull(canRoleWrite);

        createRoleIfNotExisting(Um.Roles.ROLE_ADMIN, Sets.<Privilege> newHashSet(canRoleRead, canRoleWrite, canPrivilegeRead, canPrivilegeWrite));
        createRoleIfNotExisting(Um.Roles.ROLE_USER, Sets.<Privilege> newHashSet());
    }

    final void createRoleIfNotExisting(final String name, final Set<Privilege> privileges) {
        final Role entityByName = roleService.findByName(name);
        if (entityByName == null) {
            final Role entity = new Role(name);
            entity.setPrivileges(privileges);
            roleService.create(entity);
        }
    }

    // Principal
    final void createPrincipals() {
        final Role roleAdmin = roleService.findByName(Um.Roles.ROLE_ADMIN);
        final Role roleUser = roleService.findByName(Um.Roles.ROLE_USER);

        createPrincipalIfNotExisting(Um.ADMIN_USER, Um.ADMIN_EMAIL, Um.ADMIN_PASS, Sets.<Role> newHashSet(roleAdmin, roleUser));
        createPrincipalIfNotExisting(Um.ARMIN_USER, Um.ARMIN_EMAIL, Um.ARMIN_PASS, Sets.<Role> newHashSet(roleAdmin, roleUser));
    }

    final void createPrincipalIfNotExisting(final String loginName, final String email, final String pass, final Set<Role> roles) {
        final Principal entityByName = principalService.findByName(loginName);
        if (entityByName == null) {
            final Principal entity = new Principal(loginName, email, pass, roles);
            entity.setCreated(new Timestamp(new Date().getTime()));
            principalService.create(entity);
        }
    }

}
