package uz.pentagol.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pentagol.dto.ArticleDTO;
import uz.pentagol.service.ArticleService;

@RestController
@RequestMapping("/article")
public class ArticleController {

    private final ArticleService articleService;

    public ArticleController(ArticleService articleService){
        this.articleService = articleService;
    }

    @GetMapping("/list")
    public ResponseEntity<?> getArticlesPagination(@RequestParam int page, @RequestParam int size){
        Page<ArticleDTO> response = articleService.getArticlesPagination(page, size);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/create")
    public ResponseEntity<?> createArticle(@RequestBody ArticleDTO dto){
        String response = articleService.createArticle(dto);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getArticleById(@PathVariable int id){
        ArticleDTO dto = articleService.getArticleById(id);

        return ResponseEntity.ok(dto);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateArticle(@RequestBody ArticleDTO articleDTO, @PathVariable int id){
        int response = articleService.updateArticle(articleDTO, id);

        if(response ==1 )
            return ResponseEntity.ok().build();

        return ResponseEntity.badRequest().build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteArticle(@PathVariable int id){
        boolean res = articleService.deleteArticle(id);
        return ResponseEntity.ok(res);
    }
}
