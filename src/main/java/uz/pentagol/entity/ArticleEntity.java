package uz.pentagol.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "article")
@Getter
@Setter
public class ArticleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "title_en")
    private String titleEn;
    @Column(name = "title_uz")
    private String titleUz;
    @Column(name = "body_en")
    private String bodyEn;
    @Column(name = "body_uz")
    private String bodyUz;
    @Column(name = "descriptionEn")
    private String descriptionEn;
    @Column(name = "descriptionUz")
    private String descriptionUz;
    @Column(name = "published_at")
    private LocalDateTime publishedAt;
}
