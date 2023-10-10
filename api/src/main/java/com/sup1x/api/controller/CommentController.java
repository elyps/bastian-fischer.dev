package com.sup1x.api.controller;

import com.sup1x.api.model.Article;
import com.sup1x.api.model.Comment;
import com.sup1x.api.repository.ArticleRepository;
import com.sup1x.api.repository.CommentRepository;
import com.sup1x.api.service.ArticleService;
import com.sup1x.api.service.CommentService;
import com.sup1x.api.service.NotificationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.Table;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600, allowCredentials = "true")
@Tag(name = "Comments V1", description = "The Comments V1 Management API")
@Table(name = "comments")
public class CommentController {
    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    ArticleRepository articleRepository;

    private final ArticleService articleService;

    @Autowired
    private CommentService commentService;

    @Autowired
    public CommentController(CommentRepository commentRepository, NotificationService notificationService, ArticleService articleService, CommentService commentService, ArticleRepository articleRepository) {
        this.commentRepository = commentRepository;
        this.notificationService = notificationService;
        this.articleService = articleService;
        this.commentService = commentService;
        this.articleRepository = articleRepository;
    }

    @GetMapping("/comments/all")
    public ResponseEntity<Iterable<Comment>> getAllComments() {
        Iterable<Comment> comments = commentRepository.findAll();
        return new ResponseEntity<>(comments, HttpStatus.OK);
    }

/*    @PostMapping("/articles/{id}/comments/add")
    public ResponseEntity<Comment> createComment(long id, @RequestBody Comment comment) {
        try {
            // Holen Sie das Authentifizierungsobjekt aus dem SecurityContextHolder
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication != null && authentication.isAuthenticated() && authentication.getPrincipal() instanceof UserDetails) {
                UserDetails userDetails = (UserDetails) authentication.getPrincipal();
                String username = userDetails.getUsername();

                Optional<Article> articleData = articleRepository.findById((id));

                if (articleData.isPresent()) {
                    Article article = articleData.get();

                    Comment newComment = new Comment(
                            comment.getId(),
                            comment.getContent(),
                            comment.getCommentAuthor(), // Use the authenticated username
                            comment.getCreatedAt(),
                            comment.getUpdatedAt(),
                            comment.getArticleId() // Use the provided article id
                    );

                    Comment createdComment = commentRepository.save(newComment);
                    notificationService.sendNotification("Neuer Kommentar erstellt: " + comment.getContent());
                    return new ResponseEntity<>(createdComment, HttpStatus.CREATED);
                } else {
                    return new ResponseEntity<>(null, HttpStatus.NOT_FOUND); // Article not found
                }
            } else {
                return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }*/


/*    @PostMapping("/comments/add")
    public ResponseEntity<Comment> createComment(@RequestBody Comment comment) {
        // Holen Sie das Authentifizierungsobjekt aus dem SecurityContextHolder
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() && authentication.getPrincipal() instanceof UserDetails) {

            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String username = userDetails.getUsername();

            Comment newComment = new Comment(
                    comment.getContent(),
                    comment.getCommentAuthor(),
                    comment.getCreatedAt(),
                    comment.getUpdatedAt()
            );

            newComment.setCommentAuthor(username);

            try {
                Comment createdComment = commentRepository.save(newComment);
                notificationService.sendNotification("Neuer Kommentar erstellt: " + comment.getContent());
                return new ResponseEntity<>(createdComment, HttpStatus.CREATED);
            } catch (Exception e) {
                return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }
    }*/

    /*@PostMapping("/comments/add")
    public ResponseEntity<Comment> createComment(@RequestBody Comment comment, @RequestParam Long articleId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() && authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String username = userDetails.getUsername();
            LocalDate createdAt = LocalDate.now();
            LocalDate updatedAt = LocalDate.now();

            Comment newComment = new Comment(
                    comment.getContent(),
                    comment.getCommentAuthor(),
                    comment.getCreatedAt(),
                    comment.getUpdatedAt(),
                    comment.getArticleId()
            );

            newComment.setCommentAuthor(username);
            newComment.setCreatedAt(createdAt);
            newComment.setUpdatedAt(updatedAt);
            newComment.setArticleId(articleId); // Füge die article_id hinzu

            try {
                Comment createdComment = commentRepository.save(newComment);
                notificationService.sendNotification("Neuer Kommentar erstellt: " + comment.getContent());
                return new ResponseEntity<>(createdComment, HttpStatus.CREATED);
            } catch (Exception e) {
                return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }
    }*/

    @PostMapping("/comments/add")
    public ResponseEntity<Comment> createComment(@RequestBody Comment comment, @RequestParam Long articleId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() && authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String username = userDetails.getUsername();
            String createdAt = LocalDate.now() + " " + new java.util.Date().getHours() + ":" + new java.util.Date().getMinutes() + ":" + new java.util.Date().getSeconds();
            String updatedAt = LocalDate.now() + " " + new java.util.Date().getHours() + ":" + new java.util.Date().getMinutes() + ":" + new java.util.Date().getSeconds();

            try {
                // Überprüfe, ob der Artikel mit der gegebenen articleId existiert
                Optional<Article> optionalArticle = articleRepository.findById(articleId);

                if (optionalArticle.isPresent()) {
                    Article article = optionalArticle.get();

                    Comment newComment = new Comment(
                            comment.getContent(),
                            comment.getCommentAuthor(),
                            comment.getCreatedAt(),
                            comment.getUpdatedAt()
                    );

                    newComment.setCommentAuthor(username); // Füge den Benutzernamen zum Kommentar hinzu
                    newComment.setCreatedAt(createdAt); // Füge das aktuelle Datum zum Kommentar hinzu
                    newComment.setUpdatedAt(updatedAt); // Füge das aktuelle Datum zum Kommentar hinzu
                    newComment.setArticle(article); // Füge den Artikel zum Kommentar hinzu

                    Comment createdComment = commentRepository.save(newComment);
                    notificationService.sendNotification("Neuer Kommentar erstellt: " + comment.getContent());
                    return new ResponseEntity<>(createdComment, HttpStatus.CREATED);
                } else {
                    return new ResponseEntity<>(null, HttpStatus.NOT_FOUND); // Artikel nicht gefunden
                }
            } catch (Exception e) {
                return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }
    }


    @GetMapping("/comments/{id}")
    public ResponseEntity<Comment> getCommentById(@PathVariable Long id) {
        Optional<Comment> comment = commentRepository.findById(id);
        if (comment.isPresent()) {
            return new ResponseEntity<>(comment.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/comments/update/{id}")
    public ResponseEntity<Comment> updateComment(@PathVariable Long id, @RequestBody Comment updatedComment) {
        Optional<Comment> existingComment = commentRepository.findById(id);
        if (existingComment.isPresent()) {
            Comment commentToUpdate = existingComment.get();
            commentToUpdate.setContent(updatedComment.getContent());
            // You can update other fields as needed

            Comment updated = commentRepository.save(commentToUpdate);
            return new ResponseEntity<>(updated, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/comments/delete/id/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long id) {
        Optional<Comment> comment = commentRepository.findById(id);
        if (comment.isPresent()) {
            commentRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
