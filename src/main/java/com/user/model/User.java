package com.user.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;

@ToString
@EqualsAndHashCode(of = ("ssn"))
public class User {

    @Getter
    @Setter
    private String firstName;

    @Getter
    @Setter
    private String lastName;

    @Getter
    @Setter
    @Email
    private String email;

    @Getter
    @Setter
    @Pattern(regexp="\\d{3}-\\d{2}-\\d{4}")
    private String ssn;

}
