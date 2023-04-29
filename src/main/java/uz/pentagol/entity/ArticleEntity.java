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
    @Column
    private String title;
    @Column
    private String body;
    @Column(name = "published_at")
    private LocalDateTime publishedAt;
    @Lob
    @Column
    private byte[] image;
}
