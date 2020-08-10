package de.avensio.common.util;

public final class Um {

    private Um() {
        throw new AssertionError();
    }

    public static final String ARMIN_USER = "armin";
    public static final String ARMIN_PASS = "$2a$10$iF8J.Ce.UqcC/Df7XZX/MOfjoQI9VzzsvS31ZOHQOOVtQXY4k287m";
    public static final String ARMIN_EMAIL = "armin@avensio.de";

    public static final String ADMIN_USER = "admin";
    public static final String ADMIN_PASS = "$2a$10$iF8J.Ce.UqcC/Df7XZX/MOfjoQI9VzzsvS31ZOHQOOVtQXY4k287m";
    public static final String ADMIN_EMAIL = "admin@avensio.de";

    // privileges

    public static final class Privileges {

        // Role
        public static final String CAN_ROLE_READ = "ROLE_ROLE_READ";
        public static final String CAN_ROLE_WRITE = "ROLE_ROLE_WRITE";

        // Privilege
        public static final String CAN_PRIVILEGE_READ = "ROLE_PRIVILEGE_READ";
        public static final String CAN_PRIVILEGE_WRITE = "ROLE_PRIVILEGE_WRITE";


        // End user privileges

    }

    public static final class Roles {

        /** A placeholder role for administrator. */
        public static final String ROLE_ADMIN = "ROLE_ADMIN";
        /** A placeholder role for enduser. */
        public static final String ROLE_USER = "ROLE_USER";

    }

}
