package uz.pentagol.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.pentagol.entity.MatchEntity;
import uz.pentagol.mapper.MatchMapper;

import java.time.LocalDateTime;
import java.util.List;

public interface MatchRepository extends JpaRepository<MatchEntity, Integer> {


    @Query("select m from MatchEntity m" +
            " left join ClubEntity as c1 on m.clubAId = c1.id" +
            " left join ClubEntity as c2 on m.clubBId = c2.id" +
            " where m.matchDate between ?1 and ?2 and m.leagueId = ?3")
    List<MatchEntity> getPreviousWeekMatches(LocalDateTime firstDateTimeOfWeek, LocalDateTime lastDateTimeOfWeek, int leagueId);

    @Query("select m from MatchEntity m" +
            " left join ClubEntity as c1 on m.clubAId = c1.id" +
            " left join ClubEntity as c2 on m.clubBId = c2.id" +
            " where m.matchDate between ?1 and ?2 and m.leagueId = ?3")
    List<MatchEntity> getNextWeekMatches(LocalDateTime firstDateOfCurrentWeek, LocalDateTime lastDateOfCurrentWeek, int leagueId);
}
