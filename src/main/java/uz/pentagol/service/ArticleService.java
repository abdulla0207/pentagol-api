package uz.pentagol.service;

import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import uz.pentagol.dto.ArticleDTO;
import uz.pentagol.dto.JwtDTO;
import uz.pentagol.entity.ArticleEntity;
import uz.pentagol.enums.UserRoleEnum;
import uz.pentagol.exceptions.AppForbiddenException;
import uz.pentagol.exceptions.ArticleCreateException;
import uz.pentagol.exceptions.ItemNotFound;
import uz.pentagol.repository.ArticleRepository;

import java.time.LocalDateTime;
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
        Pageable pageable = PageRequest.of(page, size, Sort.by("publishedAt").descending());

        Page<ArticleEntity> articleEntities = articleRepository.findAll(pageable);

        List<ArticleEntity> list = articleEntities.get().toList();

        List<ArticleDTO> response = toDtoList(list);

        return new PageImpl<>(response, pageable, articleEntities.getTotalElements());

    }

    public ArticleDTO createArticle(ArticleDTO articleDTO, JwtDTO jwtDTO){
        if (!jwtDTO.getRoleEnum().equals(UserRoleEnum.ADMIN))
            throw new AppForbiddenException("Method not Allowed");

        if(articleDTO.getTitle().trim().isBlank())
            throw new ArticleCreateException("Title of the article should be filled");
        if(articleDTO.getBody().isBlank())
            throw new ArticleCreateException("Body of the article should be filled");
        if(articleDTO.getDescription().isBlank())
            throw new ArticleCreateException("The article should have description");

        ArticleEntity save = articleRepository.save(toEntity(articleDTO));
        articleDTO.setId(save.getId());
        return articleDTO;
    }

    public ArticleDTO getArticleById(int id){
        Optional<ArticleEntity> entity = articleRepository.findById(id);

        if (entity.isEmpty())
            throw new ItemNotFound("Article with this id not found");

        ArticleEntity entity1 = entity.get();

        return toDto(entity1);
    }

    public int updateArticle(ArticleDTO articleDTO, int id, JwtDTO jwtDTO){
        if (!jwtDTO.getRoleEnum().equals(UserRoleEnum.ADMIN))
            throw new AppForbiddenException("Method not Allowed");
        int res = articleRepository.updateArticle(articleDTO.getBody(), articleDTO.getDescription(), articleDTO.getTitle(), id);

        return res;
    }

    private ArticleDTO toDto(ArticleEntity entity){
        ArticleDTO dto = new ArticleDTO();

        dto.setDescription(entity.getDescriptionEn());
        dto.setTitle(entity.getTitleEn());
        dto.setBody(entity.getBodyEn());
        dto.setPublishedAt(entity.getPublishedAt().toString());

        return dto;
    }
    private ArticleEntity toEntity(ArticleDTO dto){
        ArticleEntity entity = new ArticleEntity();

        entity.setBodyEn(dto.getBody());
        entity.setTitleEn(dto.getTitle());
        entity.setPublishedAt(LocalDateTime.now());
        entity.setDescriptionEn(dto.getDescription());

        return entity;
    }
    private List<ArticleDTO> toDtoList(List<ArticleEntity> articleEntities){
        List<ArticleDTO> articleDTOS = new ArrayList<>();

        articleEntities.forEach(e ->{
            ArticleDTO articleDTO = new ArticleDTO();
            articleDTO.setTitle(e.getTitleEn());
            articleDTO.setBody(e.getBodyEn());
            articleDTO.setPublishedAt(e.getPublishedAt().toString());
            articleDTO.setDescription(e.getDescriptionEn());
            articleDTO.setId(e.getId());
            articleDTOS.add(articleDTO);
        });

        return articleDTOS;
    }

    public boolean deleteArticle(int id, JwtDTO jwtDTO) {
        if (!jwtDTO.getRoleEnum().equals(UserRoleEnum.ADMIN))
            throw new AppForbiddenException("Method not Allowed");
        Optional<ArticleEntity> getById = articleRepository.findById(id);

        if(getById.isEmpty())
            return false;

        articleRepository.delete(getById.get());
        return true;
    }
}
