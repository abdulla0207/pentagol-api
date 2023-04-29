package uz.pentagol.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Table(name = "match")
@Entity
@Getter
@Setter
public class MatchEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "club_a_id")
    private int clubAId;
    @ManyToOne
    @JoinColumn(name = "club_a_id", insertable = false, updatable = false)
    private ClubEntity clubA;
    @Column(name = "club_b_id")
    private int clubBId;
    @ManyToOne
    @JoinColumn(name = "club_b_id", insertable = false, updatable = false)
    private ClubEntity clubB;
    @Column(name = "club_a_score")
    private int clubAScore;
    @Column(name = "club_b_score")
    private int clubBScore;

    @Column(name = "match_date")
    private LocalDateTime matchDate;

}
