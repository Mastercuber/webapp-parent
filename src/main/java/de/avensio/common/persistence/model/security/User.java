package de.avensio.common.persistence.model.security;

import de.avensio.common.validation.PasswordMatches;
import de.avensio.common.validation.ValidPassword;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.util.Calendar;


@Entity
@PasswordMatches
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Email
    @NotEmpty(message = "Email is required.")
    private String email;

    @ValidPassword
    @NotEmpty(message = "Password is required.")
    private String password;

    @Transient
    @NotEmpty(message = "Password confirmation is required.")
    private String passwordConfirmation;

    @Column
    private Boolean enabled;

    private Calendar created = Calendar.getInstance();

    public User() {
        super();
        this.enabled = false;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Calendar getCreated() {
        return this.created;
    }

    public void setCreated(Calendar created) {
        this.created = created;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    public String getPasswordConfirmation() {
        return passwordConfirmation;
    }

    public void setPasswordConfirmation(final String passwordConfirmation) {
        this.passwordConfirmation = passwordConfirmation;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(final Boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("email", email)
                .append("password", password)
                .append("passwordConfirmation", passwordConfirmation)
                .append("created", created)
                .toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(327, 1217)
                .append(id)
                .append(email)
                .append(password)
                .append(passwordConfirmation)
                .append(created)
                .toHashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;

        return new EqualsBuilder()
                .append(id, user.id)
                .append(email, user.email)
                .append(enabled, user.enabled)
                .append(created, user.created)
                .append(password, user.password)
                .append(passwordConfirmation, user.passwordConfirmation)
                .isEquals();
    }
}
