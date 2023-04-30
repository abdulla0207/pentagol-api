package uz.pentagol.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pentagol.dto.ArticleDTO;
import uz.pentagol.dto.JwtDTO;
import uz.pentagol.service.ArticleService;
import uz.pentagol.util.JwtUtil;

@RestController
@RequestMapping("/article")
@CrossOrigin(origins = "*", allowedHeaders = "*")
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
    public ResponseEntity<?> createArticle(@RequestBody ArticleDTO dto, @RequestHeader("Authorization") String headerToken){
        JwtDTO jwtDTO = JwtUtil.decode(JwtUtil.getToken(headerToken));
        ArticleDTO response = articleService.createArticle(dto, jwtDTO);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getArticleById(@PathVariable int id){
        ArticleDTO dto = articleService.getArticleById(id);

        return ResponseEntity.ok(dto);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateArticle(@RequestBody ArticleDTO articleDTO, @PathVariable int id, @RequestHeader("Authorization") String headerToken){
        JwtDTO jwtDTO = JwtUtil.decode(JwtUtil.getToken(headerToken));
        int response = articleService.updateArticle(articleDTO, id, jwtDTO);

        if(response ==1 )
            return ResponseEntity.ok(articleDTO);

        return ResponseEntity.badRequest().build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteArticle(@PathVariable int id, @RequestHeader("Authorization") String headerToken){
        JwtDTO jwtDTO = JwtUtil.decode(JwtUtil.getToken(headerToken));
        boolean res = articleService.deleteArticle(id, jwtDTO);
        return ResponseEntity.ok(res);
    }
}
