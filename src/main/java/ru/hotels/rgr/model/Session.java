package ru.hotels.rgr.model;

import lombok.*;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "`session`")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Session {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true)
    private String cookie;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userid", foreignKey = @ForeignKey(name = "S_USER"))
    private User user;

    public Session(String cookie, User user) {
        this.cookie = cookie;
        this.user = user;
    }
}
