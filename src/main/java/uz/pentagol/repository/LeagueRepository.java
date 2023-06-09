package uz.pentagol.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import uz.pentagol.entity.LeagueEntity;

import java.util.Optional;

public interface LeagueRepository extends JpaRepository<LeagueEntity, Integer> {

    @Query("select l from LeagueEntity as l where l.nameEn=?1")
    Optional<LeagueEntity> findByName(String name);

    @Modifying
    @Transactional
    @Query("update LeagueEntity as l set l.nameEn = ?1 where l.id = ?2")
    int updateLeague(String name, int id);
}
