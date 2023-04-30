package uz.pentagol.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uz.pentagol.dto.ArticleDTO;
import uz.pentagol.entity.ArticleEntity;
import uz.pentagol.exceptions.ItemNotFound;
import uz.pentagol.repository.ArticleRepository;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ArticleService {

    private final ArticleRepository articleRepository;

    public ArticleService(ArticleRepository articleRepository){
        this.articleRepository = articleRepository;
    }

    public Page<ArticleDTO> getArticlesPagination(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        Page<ArticleEntity> articleEntities = articleRepository.findAll(pageable);

        List<ArticleEntity> list = articleEntities.get().toList();

        List<ArticleDTO> response = toDtoList(list);

        return new PageImpl<>(response, pageable, articleEntities.getTotalElements());

    }

    public String createArticle(ArticleDTO articleDTO){
        articleRepository.save(toEntity(articleDTO));

        return "Article created";
    }

    public ArticleDTO getArticleById(int id){
        Optional<ArticleEntity> entity = articleRepository.findById(id);

        if (entity.isEmpty())
            throw new ItemNotFound("Article with this id not found");

        ArticleEntity entity1 = entity.get();

        return toDto(entity1);
    }

    public int updateArticle(ArticleDTO articleDTO, int id){
        int res = articleRepository.updateArticle(articleDTO.getBody(), articleDTO.getDescription(), articleDTO.getTitle(), articleDTO.getImage(), id);

        return res;
    }

    private ArticleDTO toDto(ArticleEntity entity){
        ArticleDTO dto = new ArticleDTO();

        dto.setDescription(entity.getDescription());
        dto.setTitle(entity.getTitle());
        dto.setBody(entity.getBody());
        dto.setImage(entity.getImage());
        dto.setPublishedAt(entity.getPublishedAt());

        return dto;
    }
    private ArticleEntity toEntity(ArticleDTO dto){
        ArticleEntity entity = new ArticleEntity();

        entity.setBody(dto.getBody());
        entity.setTitle(dto.getTitle());
        entity.setImage(dto.getImage());
        entity.setPublishedAt(dto.getPublishedAt());
        entity.setDescription(dto.getDescription());
    }
    private List<ArticleDTO> toDtoList(List<ArticleEntity> articleEntities){
        List<ArticleDTO> articleDTOS = new ArrayList<>();

        articleEntities.forEach(e ->{
            ArticleDTO articleDTO = new ArticleDTO();
            articleDTO.setTitle(e.getTitle());
            articleDTO.setBody(e.getBody());
            articleDTO.setImage(e.getImage());
            articleDTO.setPublishedAt(e.getPublishedAt());
            articleDTO.setDescription(e.getDescription());

            articleDTOS.add(articleDTO);
        });

        return articleDTOS;
    }

    public boolean deleteArticle(int id) {
        Optional<ArticleEntity> getById = articleRepository.findById(id);

        if(getById.isEmpty())
            return false;

        articleRepository.delete(getById.get());
        return true;
    }
}
