package com.avoinovan.drawlot.model.entity;

import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection = "users")
public class User  extends AbstractEntity {

    private String email;

    private String firstName;

    private String lastName;

    private String nickname;

    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(final String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(final String lastName) {
        this.lastName = lastName;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(final String nickname) {
        this.nickname = nickname;
    }
}
