package uz.pentagol.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Column;
import jakarta.persistence.Lob;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ArticleDTO {
    private int id;
    @NotBlank @Size(min = 5, max = 1000, message = "Title is required and it should be between 5 and 1000 characters")
    private String title;
    @NotBlank @Size(min = 5, message = "Body of article is required")
    private String body;
    private LocalDateTime publishedAt;
    @NotBlank(message = "Image of article should be provided")
    private byte[] image;
}
