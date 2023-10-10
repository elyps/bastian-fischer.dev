package com.sup1x.api.controller;

import com.sup1x.api.exception.ResourceNotFoundException;
import com.sup1x.api.model.Article;
import com.sup1x.api.model.Comment;
import com.sup1x.api.repository.ArticleRepository;
import com.sup1x.api.repository.CommentRepository;
import com.sup1x.api.repository.TagRepository;
import com.sup1x.api.service.ArticleService;
import com.sup1x.api.service.NotificationService;

import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.*;

//@CrossOrigin(origins = "http://localhost:8083")
@CrossOrigin(origins = "http://localhost:8083", maxAge = 3600, allowCredentials = "true")
@Tag(name = "Article V1", description = "Article V1 Management API")
@RestController
@RequestMapping("/api/v1/articles")
public class ArticleController {

    @Autowired
    ArticleRepository articleRepository;

    private final ArticleService articleService;

    private final NotificationService notificationService;

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    public ArticleController(ArticleService articleService, NotificationService notificationService,
            TagRepository tagRepository) {
        this.articleService = articleService;
        this.notificationService = notificationService;
        this.tagRepository = tagRepository;
    }

    private Sort.Direction getSortDirection(String direction) {
        if (direction.equals("asc")) {
            return Sort.Direction.ASC;
        } else if (direction.equals("desc")) {
            return Sort.Direction.DESC;
        }

        return Sort.Direction.ASC;
    }

    @GetMapping("/sortedarticles")
    public ResponseEntity<List<Article>> getAllArticles(@RequestParam(defaultValue = "id,desc") String[] sort) {

        try {
            List<Sort.Order> orders = new ArrayList<Sort.Order>();

            if (sort[0].contains(",")) {
                // will sort more than 2 fields
                // sortOrder="field, direction"
                for (String sortOrder : sort) {
                    String[] _sort = sortOrder.split(",");
                    orders.add(new Sort.Order(getSortDirection(_sort[1]), _sort[0]));
                }
            } else {
                // sort=[field, direction]
                orders.add(new Sort.Order(getSortDirection(sort[1]), sort[0]));
            }

            List<Article> articles = articleRepository.findAll(Sort.by(orders));

            if (articles.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(articles, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /*
     * @GetMapping("/all")
     * public ResponseEntity<List<Article>> getAllArticles(@RequestParam(required =
     * false) String title) {
     * try {
     * List<Article> articles;
     * if (title == null) {
     * articles = articleRepository.findAll();
     * } else {
     * articles = articleRepository.findByTitleContaining(title);
     * }
     * if (articles.isEmpty()) {
     * return ResponseEntity.noContent().build();
     * }
     * return ResponseEntity.ok(articles);
     * } catch (Exception e) {
     * return ResponseEntity.notFound().build();
     * }
     * }
     */

    /*
     * @GetMapping("/all")
     * public ResponseEntity<List<Article>> getAllArticlesWithTags() {
     * List<Article> articles = articleRepository.findAll();
     * return ResponseEntity.ok(articles);
     * }
     */

    @GetMapping("/all")
    public ResponseEntity<Map<String, Object>> getAllArticlesPage(
            @RequestParam(required = false) String title,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id,desc") String[] sort) {

        try {
            List<Sort.Order> orders = new ArrayList<Sort.Order>();

            if (sort[0].contains(",")) {
                // will sort more than 2 fields
                // sortOrder="field, direction"
                for (String sortOrder : sort) {
                    String[] _sort = sortOrder.split(",");
                    orders.add(new Sort.Order(getSortDirection(_sort[1]), _sort[0]));
                }
            } else {
                // sort=[field, direction]
                orders.add(new Sort.Order(getSortDirection(sort[1]), sort[0]));
            }

            List<Article> articles = new ArrayList<Article>();
            Pageable pagingSort = PageRequest.of(page, size, Sort.by(orders));

            Page<Article> pageArts;
            if (title == null)
                pageArts = articleRepository.findAll(pagingSort);
            else
                pageArts = articleRepository.findByTitleContaining(title, pagingSort);

            articles = pageArts.getContent();

            Map<String, Object> response = new HashMap<>();
            response.put("articles", articles);
            response.put("currentPage", pageArts.getNumber());
            response.put("totalItems", pageArts.getTotalElements());
            response.put("totalPages", pageArts.getTotalPages());

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /*
     * @GetMapping("/{id}")
     * public ResponseEntity<Article> getArticleById(@PathVariable(value = "id") int
     * id) {
     * try {
     * Article article = articleRepository.findById((long) id).orElseThrow(() -> new
     * Exception("Article not found for this id :: " + id));
     * return ResponseEntity.ok(article);
     * } catch (Exception e) {
     * return ResponseEntity.notFound().build();
     * }
     * }
     */

    @GetMapping("/{id}")
    public ResponseEntity<Article> getArticleById(@PathVariable("id") long id) {
        Optional<Article> articleData = articleRepository.findById(id);

        if (articleData.isPresent()) {
            return new ResponseEntity<>(articleData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/slug/{slug}")
    public ResponseEntity<Article> findArticleBySlug(@PathVariable("slug") String slug) {
        Optional<Article> articleData = articleRepository.findArticleBySlug(slug);

        if (articleData.isPresent()) {
            return new ResponseEntity<>(articleData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<Article> createArticle(@RequestBody Article article) {
        // Get the authentication object from the SecurityContextHolder
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()
                && authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String username = userDetails.getUsername();

            // Create a new Article instance
            Article newArticle = new Article(
                    (int) article.getId(),
                    article.getSlug(),
                    article.getTitle(),
                    article.getContent(),
                    article.getCategory(),
                    /* article.getTags(), */
                    article.getImages(),
                    article.getDescription(),
                    article.getUrl(),
                    article.getLanguages(),
                    article.getPublished(),
                    article.getCreatedAt(),
                    article.getUpdatedAt());

            // Set the author to the logged-in user's username
            newArticle.setAuthor(username);

            try {
                Article savedArticle = articleRepository.save(newArticle);
                notificationService.sendNotification("New article created: " + article.getTitle());

                return new ResponseEntity<>(savedArticle, HttpStatus.CREATED);
            } catch (Exception e) {
                return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<Article> updateArticle(@PathVariable("id") long id, @RequestBody Article article) {
        Optional<Article> articleData = articleRepository.findById(id);

        if (articleData.isPresent()) {
            Article _article = articleData.get();
            if (article.getTitle() != null) {
                _article.setTitle(article.getTitle());
            }

            if (article.getContent() != null) {
                _article.setContent(article.getContent());
            }

            if (article.getAuthor() != null) {
                _article.setAuthor(article.getAuthor());
            }

            if (article.getCategory() != null) {
                _article.setCategory(article.getCategory());
            }

            // if (article.getTags() != null) {
            // _article.setTags(article.getTags());
            // }

            if (article.getImages() != null) {
                _article.setImages(article.getImages());
            }

            if (article.getDescription() != null) {
                _article.setDescription(article.getDescription());
            }

            if (article.getUrl() != null) {
                _article.setUrl(article.getUrl());
            }

            if (article.getLanguages() != null) {
                _article.setLanguages(article.getLanguages());
            }

            // _article.setPublished(true);
            // article.setCreatedAt(LocalDate.now());
            _article.setUpdatedAt(LocalDate.now());
            return new ResponseEntity<>(articleRepository.save(_article), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<HttpStatus> deleteArticleById(@PathVariable(value = "id") int id) {
        try {
            articleRepository.deleteById((long) id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/delete/all")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<HttpStatus> deleteAllArticles() {
        try {
            articleRepository.deleteAll();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /*
     * @GetMapping("/published")
     * public ResponseEntity<List<Article>> findByPublished() {
     * try {
     * List<Article> articles = articleRepository.findByPublished(true);
     * if (articles.isEmpty()) {
     * return ResponseEntity.noContent().build();
     * }
     * return ResponseEntity.ok(articles);
     * } catch (Exception e) {
     * return ResponseEntity.notFound().build();
     * }
     * }
     */

    @GetMapping("/published")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<Map<String, Object>> findByPublished(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size) {

        try {
            List<Article> articles = new ArrayList<Article>();
            Pageable paging = PageRequest.of(page, size);

            Page<Article> pageArts = articleRepository.findByPublished(true, paging);
            articles = pageArts.getContent();

            Map<String, Object> response = new HashMap<>();
            response.put("articles", articles);
            response.put("currentPage", pageArts.getNumber());
            response.put("totalItems", pageArts.getTotalElements());
            response.put("totalPages", pageArts.getTotalPages());

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /*
     * @GetMapping("/tags/all")
     * public ResponseEntity<List<com.sup1x.api.model.Tag>> getAllTags() {
     * List<com.sup1x.api.model.Tag> tags = tagRepository.findAll();
     * return ResponseEntity.ok(tags);
     * }
     */

    /*
     * @GetMapping("/{id}/tags")
     * public ResponseEntity<List<com.sup1x.api.model.Tag>>
     * getTagsForArticleAndTitle(
     * 
     * @RequestParam("articleId") int articleId,
     * 
     * @RequestParam("title") String title) {
     * 
     * // Fügen Sie hier den Code hinzu, um Tags für den angegebenen Artikel und
     * Titel abzurufen
     * List<com.sup1x.api.model.Tag> tags = articleService.getTagsIn(articleId,
     * title);
     * 
     * if (tags.isEmpty()) {
     * return ResponseEntity.noContent().build();
     * }
     * 
     * return ResponseEntity.ok(tags);
     * }
     */

    /*
     * @GetMapping("/tags")
     * public ResponseEntity<List<String>> getAllTags() {
     * List<String> tags = tagRepository.findAllTags();
     * return ResponseEntity.ok(tags);
     * }
     */

    /*
     * @GetMapping("/tags")
     * public ResponseEntity<List<Tag>> getAllTags() {
     * List<Tag> tags = (List<Tag>) tagRepository.findAll();
     * return ResponseEntity.ok(tags);
     * }
     */

    /*
     * @GetMapping("/tags")
     * public ResponseEntity<List<String>> findTagsByTags(@RequestParam(required =
     * false) List<String> tags) {
     * try {
     * List<String> uniqueTags = new ArrayList<>();
     * 
     * if (tags == null || tags.isEmpty()) {
     * // Wenn keine Tags angegeben sind, alle eindeutigen Tags aus der Datenbank
     * abrufen.
     * List<Article> articles = articleRepository.findAll();
     * for (Article article : articles) {
     * uniqueTags.addAll(article.getTags());
     * }
     * } else {
     * // Andernfalls Tags aus Artikeln mit den angegebenen Tags abrufen.
     * List<Article> articles = articleRepository.findByTagsIn(tags);
     * for (Article article : articles) {
     * uniqueTags.addAll(article.getTags());
     * }
     * }
     * 
     * // Die Liste der eindeutigen Tags erstellen (doppelte Tags entfernen).
     * List<String> distinctTags = uniqueTags.stream()
     * .distinct()
     * .collect(Collectors.toList());
     * 
     * if (distinctTags.isEmpty()) {
     * return ResponseEntity.noContent().build();
     * }
     * 
     * return ResponseEntity.ok(distinctTags);
     * } catch (Exception e) {
     * return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
     * }
     * }
     */

    // @GetMapping("/tags")
    // public ResponseEntity<List<Article>> findByTags(@RequestParam(required =
    // false) List<String> tags) {
    // try {
    // List<Article> articles;
    // if (tags == null || tags.isEmpty()) {
    // articles = articleRepository.findAll();
    // } else {
    // articles = articleRepository.findByTagsIn(tags);
    // }
    // if (articles.isEmpty()) {
    // return ResponseEntity.noContent().build();
    // }
    // return ResponseEntity.ok(articles);
    // } catch (Exception e) {
    // return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    // }
    // }

    @GetMapping("/languages")
    public ResponseEntity<List<Article>> findByLanguages(@RequestParam(required = false) List<String> languages) {
        try {
            List<Article> articles;

            // Check if the "language" parameter is not provided in the request
            if (languages == null) {
                // If no language filter is given, retrieve all articles
                articles = articleRepository.findAll();
            } else {
                // If the "language" parameter is provided, fetch articles containing the
                // specified language(s)
                articles = articleRepository.findByLanguagesIn(languages);
            }

            // If no articles are found, return a response with status code 204 (No Content)
            if (articles.isEmpty()) {
                return ResponseEntity.noContent().build();
            }

            // If articles are found, return a response with status code 200 (OK) and the
            // list of articles
            return ResponseEntity.ok(articles);
        } catch (Exception e) {
            // If any exception occurs during processing, return a response with status code
            // 404 (Not Found)
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/category")
    public ResponseEntity<List<Article>> findByCategory(@RequestParam(required = false) String category) {
        try {
            List<Article> articles;
            if (category == null) {
                articles = articleRepository.findAll();
            } else {
                articles = articleRepository.findByCategoryContaining(category);
            }
            if (articles.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(articles);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/author")
    public ResponseEntity<List<Article>> findByAuthor(@RequestParam(required = false) String author) {
        try {
            List<Article> articles;
            if (author == null) {
                articles = articleRepository.findAll();
            } else {
                articles = articleRepository.findByAuthorContaining(author);
            }
            if (articles.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(articles);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/title")
    public ResponseEntity<List<Article>> findByTitle(@RequestParam(required = false) String title) {
        try {
            List<Article> articles;
            if (title == null) {
                articles = articleRepository.findAll();
            } else {
                articles = articleRepository.findByTitleContaining(title, Sort.by(Sort.Direction.DESC, "id"));
            }
            if (articles.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(articles);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/description")
    public ResponseEntity<List<Article>> findByDescription(@RequestParam(required = false) String description) {
        try {
            List<Article> articles;
            if (description == null) {
                articles = articleRepository.findAll();
            } else {
                articles = articleRepository.findByDescriptionContaining(description);
            }
            if (articles.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(articles);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/url")
    public ResponseEntity<List<Article>> findByUrl(@RequestParam(required = false) String url) {
        try {
            List<Article> articles;
            if (url == null) {
                articles = articleRepository.findAll();
            } else {
                articles = articleRepository.findByUrlContaining(url);
            }
            if (articles.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(articles);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/createdAt")
    public ResponseEntity<List<Article>> findByCreatedAt(@RequestParam(required = false) String createdAt) {
        try {
            List<Article> articles;
            if (createdAt == null) {
                articles = articleRepository.findAll();
            } else {
                articles = articleRepository.findByCreatedAtContaining(LocalDate.parse(createdAt));
            }
            if (articles.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(articles);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/updatedAt")
    public ResponseEntity<List<Article>> findByUpdatedAt(@RequestParam(required = false) String updatedAt) {
        try {
            List<Article> articles;
            if (updatedAt == null) {
                articles = articleRepository.findAll();
            } else {
                articles = articleRepository.findByUpdatedAtContaining(LocalDate.parse(updatedAt));
            }
            if (articles.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(articles);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{id}/like")
    public void likeArticle(@PathVariable Long id) {
        articleService.likeArticle(id);
    }

    @PostMapping("/{id}/unlike")
    public void unlikeArticle(@PathVariable Long id) {
        articleService.unlikeArticle(id);
    }

    @GetMapping("/{id}/likes")
    public int getLikesCount(@PathVariable Long id) {
        return articleService.getLikesCount(id);
    }

    // Endpunkt zum Erstellen eines Kommentars für einen Beitrag
    @PostMapping("/{id}/comments")
    public Comment createComment(@PathVariable Long id, @RequestBody Comment comment) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()
                && authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String username = userDetails.getUsername();
            String createdAt = LocalDate.now() + " " + new Date().getHours() + ":" + new Date().getMinutes() + ":"
                    + new Date().getSeconds();
            String updatedAt = LocalDate.now() + " " + new Date().getHours() + ":" + new Date().getMinutes() + ":"
                    + new Date().getSeconds();

            return articleRepository.findById(id).map(article -> {
                comment.setArticle(article);
                comment.setCommentAuthor(username); // Füge den Benutzernamen zum Kommentar hinzu
                comment.setCreatedAt(createdAt); // Füge das aktuelle Datum zum Kommentar hinzu
                comment.setUpdatedAt(updatedAt); // Füge das aktuelle Datum zum Kommentar hinzu
                Comment createdComment = commentRepository.save(comment);
                notificationService.sendNotification("New comment created!");
                return new ResponseEntity<>(createdComment, HttpStatus.CREATED);
            }).orElseThrow(() -> new ResourceNotFoundException("Article not found with id " + id)).getBody();
        } else {
            return null;
        }

        /*
         * Article article = articleRepository.findById(id)
         * .orElseThrow(() -> new ResourceNotFoundException("Article not found with id "
         * + id));
         * comment.setArticle(article);
         * comment.setCommentAuthor(username); // Füge den Benutzernamen zum Kommentar
         * hinzu
         * comment.setCreatedAt(createdAt); // Füge das aktuelle Datum zum Kommentar
         * hinzu
         * comment.setUpdatedAt(updatedAt); // Füge das aktuelle Datum zum Kommentar
         * hinzu
         * return commentRepository.save(comment);
         */
    }

    // Endpunkt zum Abrufen aller Kommentare für einen Beitrag
    @GetMapping("/{id}/comments")
    public List<Comment> getCommentsForPost(@PathVariable Long id) {
        return commentRepository.findByArticleId(id);
    }

}
