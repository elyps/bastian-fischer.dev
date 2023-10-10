package com.sup1x.api.service;

import com.sup1x.api.model.Article;
import com.sup1x.api.model.Tag;
import com.sup1x.api.repository.ArticleRepository;
import com.sup1x.api.repository.UserRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

@Service
// @EnableCaching
public class ArticleService {

    @Autowired
    private final ArticleRepository articleRepository;

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    public ArticleService(ArticleRepository articleRepository, UserRepository userRepository) {
        this.articleRepository = articleRepository;
        this.userRepository = userRepository;
    }

  /*  public List<Tag> getTagsForArticleAndTitle(int articleId, String title) {
        // Fügen Sie hier den Code hinzu, um Tags für den angegebenen Artikel und Titel abzurufen
        // Verwenden Sie Ihr ArticleRepository, um die Tags zu filtern
        // Achten Sie darauf, den Artikel und den Titel als Filterkriterien zu verwenden
        return articleRepository.getTagsForArticleAndTitle(articleId, title);
    }*/


    // @Cacheable("articles")
    // public List<Article> findAll() {
    //     doLongRunningTask();

    //     return articleRepository.findAll();
    // }

    // @Cacheable("title")
    // public List<Article> findByTitleContaining(String title) {
    //     doLongRunningTask();

    //     return articleRepository.findByTitleContaining(title, Sort.by(Sort.Direction.DESC, "id"));
    // }

    // @Cacheable("{id}")
    // public Optional<Article> findById(long id) {
    //     doLongRunningTask();

    //     return articleRepository.findById(id);
    // }

    // @Cacheable("published")
    // public List<Article> findByPublished(boolean published) {
    //     doLongRunningTask();

    //     return articleRepository.findByPublished(published);
    // }

    // // other methods...

    // private void doLongRunningTask() {
    //     try {
    //         Thread.sleep(3000);
    //     } catch (InterruptedException e) {
    //         e.printStackTrace();
    //     }
    // }

    // Methode zum Hinzufügen eines "Likes" zu einem Artikel
    public void likeArticle(Long articleId) {
        Article article = articleRepository.findById(articleId).orElse(null);
        if (article != null) {
            int currentLikes = article.getLikes();
            article.setLikes(currentLikes + 1);
            articleRepository.save(article);
        }
    }

    // Methode zum Entfernen eines "Likes" von einem Artikel
    public void unlikeArticle(Long articleId) {
        Article article = articleRepository.findById(articleId).orElse(null);
        if (article != null) {
            int currentLikes = article.getLikes();
            if (currentLikes > 0) {
                article.setLikes(currentLikes - 1);
                articleRepository.save(article);
            }
        }
    }

    // Methode zum Abrufen der Anzahl der Likes eines Artikels
    public int getLikesCount(Long articleId) {
        Article article = articleRepository.findById(articleId).orElse(null);
        return article != null ? article.getLikes() : 0;
    }
}
