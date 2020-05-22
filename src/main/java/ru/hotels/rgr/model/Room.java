package ru.hotels.rgr.model;

import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "`room`")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(unique = true)
    private int number;
    @Column
    private int price;
    @Column
    private String description;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userid",foreignKey = @ForeignKey(name = "ROOM_USER"))
    private User user;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "room_specifications",
            joinColumns = @JoinColumn(name = "roomid"),
            inverseJoinColumns = @JoinColumn(name = "specificationid"))
    private List<Specification> specifications;

    @OneToMany(mappedBy = "room", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    @Fetch(FetchMode.JOIN)
    @OrderBy("id DESC")
    private List<Reservation> reservations;

}
