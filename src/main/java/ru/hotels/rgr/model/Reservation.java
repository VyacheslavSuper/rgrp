package ru.hotels.rgr.model;

import lombok.*;
import ru.hotels.rgr.model.types.ReservationType;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "`reservation`")

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private LocalDate timeStart;

    @Column
    private LocalDate timeEnd;

    @Column
    private ReservationType type = ReservationType.IN_PROGRESS;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "roomid")
    private Room room;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userid",foreignKey = @ForeignKey(name = "RES_USER"))
    private User user;
}
