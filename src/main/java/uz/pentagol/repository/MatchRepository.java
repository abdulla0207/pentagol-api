package uz.pentagol.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.pentagol.entity.MatchEntity;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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

    @Query("select m from MatchEntity as m where m.matchDate < ?1")
    List<MatchEntity> getPrevMatches(LocalDateTime currentDate);

    @Query("select m from MatchEntity as m where date(m.matchDate) = ?1 and ((m.clubAId = ?2 and m.clubBId = ?3) or (m.clubAId = ?2 or m.clubBId=?3))")
    Optional<MatchEntity> findByMatchDate(LocalDateTime matchDate, int clubAId, int clubBId);
}
