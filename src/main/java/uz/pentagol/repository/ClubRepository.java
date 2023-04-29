package uz.pentagol.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import uz.pentagol.entity.ClubEntity;

import java.util.Optional;

public interface ClubRepository extends JpaRepository<ClubEntity, Integer> {

    @Query("select c from ClubEntity as c where c.name=?1")
    Optional<ClubEntity> findByName(String name);


    @Modifying
    @Transactional
    @Query("update ClubEntity as c set c.name=?1, c.point=?2, c.leagueId=?3, c.image=?4, c.gamesPlayed=?5 where c.id = ?6")
    int updateClub(String name, int point, int leagueId, byte[] image, int gamesPlayed, int id);
}
