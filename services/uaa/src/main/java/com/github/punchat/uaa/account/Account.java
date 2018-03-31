package com.github.punchat.uaa.account;

import com.github.punchat.uaa.account.jsr303.Password;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * @author Alex Ivchenko
 */
@Getter
@Setter
@ToString(of = {"id", "username"})
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "accounts")
public class Account {
    @Id
    @Column(name = "id")
    private Long id;
    @Column(name = "username")
    @NotNull(message = "{User.username.required}")
    private String username;
    @Column(name = "password")
    @Password(message = "{User.password.required}")
    private String password;
}