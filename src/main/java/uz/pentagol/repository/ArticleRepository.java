package uz.pentagol.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import uz.pentagol.entity.ArticleEntity;

public interface ArticleRepository extends JpaRepository<ArticleEntity, Integer> {

    @Modifying
    @Transactional
    @Query("update ArticleEntity as a set a.body=?1, a.description=?2, a.title=?3 where a.id = ?4")
    int updateArticle(String body, String description, String title, int id);

}
