package uz.pentagol.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "club")
@Getter
@Setter
public class ClubEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column
    private String name;
    @Column
    private int point;
    @Column(name = "games_played")
    private int gamesPlayed;
    @Column
    @Lob
    private byte[] image;
    @Column(name = "league_id")
    private int leagueId;
    @ManyToOne
    @JoinColumn(name = "league_id", insertable = false, updatable = false)
    private LeagueEntity league;
}
