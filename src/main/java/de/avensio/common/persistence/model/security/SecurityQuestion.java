package de.avensio.common.persistence.model.security;

import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;

@Entity
@EqualsAndHashCode
@ToString
public class SecurityQuestion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "user_id", unique = true)
    private User user;

    @OneToOne(targetEntity = SecurityQuestionDefinition.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "securityQuestionDefinition_id")
    private SecurityQuestionDefinition questionDefinition;

    private String answer;

    public SecurityQuestion(final User user, final SecurityQuestionDefinition questionDefinition, final String answer) {
        this.user = user;
        this.questionDefinition = questionDefinition;
        this.answer = answer;
    }

    public SecurityQuestion() {
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(final User user) {
        this.user = user;
    }

    public SecurityQuestionDefinition getQuestionDefinition() {
        return questionDefinition;
    }

    public void setQuestionDefinition(final SecurityQuestionDefinition questionDefinition) {
        this.questionDefinition = questionDefinition;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(final String answer) {
        this.answer = answer;
    }

}
