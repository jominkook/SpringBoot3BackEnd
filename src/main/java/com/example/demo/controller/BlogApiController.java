package com.example.demo.controller;


import com.example.demo.dto.AddArticleRequest;
import com.example.demo.domain.Article;
import com.example.demo.dto.ArticleResponse;
import com.example.demo.dto.BlogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController //HTTP Response Body에 객체 데이터를 JSON 형태로 변환하는 컨트롤러
public class BlogApiController {

    private final BlogService blogService;

    //HTTP메서드가 POST일때 전달받은 URL과 동일하면 메서드로 매핑
    @PostMapping("/api/articles")
    //@RequestBody로 요청 본문 값 매핑
    public ResponseEntity<Article> addArticle(@RequestBody AddArticleRequest request) {
        Article savedArticle = blogService.save(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(savedArticle);
    }

    @GetMapping("/api/articles")
    public ResponseEntity<List<ArticleResponse>> findAllArticles() {
        List<ArticleResponse> articles = blogService.findAll()
                .stream()
                .map(ArticleResponse::new)
                .toList();

        return ResponseEntity.ok()
                .body(articles);

    }
}