package de.avensio.common.persistence.model.security;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;

@Entity
@EqualsAndHashCode
@ToString
public class SecurityQuestionDefinition {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @NotEmpty
    private String text;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(final String text) {
        this.text = text;
    }

}
