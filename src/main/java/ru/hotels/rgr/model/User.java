package ru.hotels.rgr.model;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import ru.hotels.rgr.model.types.UserType;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Entity
@Table(name = "`user`")
@Data
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true)
    private String login;

    @Column
    private String password;

    @Column
    private String name;

    @Column
    private String email;

    @Column(nullable = false)
    private UserType userType = UserType.USER;

    @Column
    private LocalDate timeRegistered;

    @Column(nullable = false)
    private Boolean deleted = false;

    @OneToOne(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Session session;

    public User(String login, String password, String name, String email, UserType userType, LocalDate timeRegistered) {
        this.login = login;
        this.password = password;
        this.name = name;
        this.email = email;
        this.userType = userType;
        this.timeRegistered = timeRegistered;
    }

}
