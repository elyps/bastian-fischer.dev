package com.sup1x.api.controller;

import com.sup1x.api.repository.TagRepository;
import com.sup1x.api.service.TagService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600, allowCredentials = "true")
@Tag(name = "Tags V1", description = "Tags V1 Management API")
@RestController
@RequestMapping("/api/v1")
public class TagController {

    private final TagRepository tagRepository;

    private final TagService tagService;

    public TagController(TagRepository tagRepository, TagService tagService) {
        this.tagRepository = tagRepository;
        this.tagService = tagService;
    }

    @GetMapping("/tags/all")
    public ResponseEntity<List<com.sup1x.api.model.Tag>> getAllTags() {
        List<com.sup1x.api.model.Tag> tags = tagRepository.findAll();
        return ResponseEntity.ok(tags);
    }

    @GetMapping("/tags/id/{id}")
    public ResponseEntity<Optional<com.sup1x.api.model.Tag>> getTagById(@PathVariable("id") long id) {
        Optional<Optional<com.sup1x.api.model.Tag>> tagData = Optional.ofNullable(tagRepository.findById(id));

        if (tagData.isPresent()) {
            return new ResponseEntity<>(tagData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /*@GetMapping("/tags/name/{name}")
    public ResponseEntity<Optional<com.sup1x.api.model.Tag>> getTagByName(@PathVariable("name") String name) {
        Optional<List<com.sup1x.api.model.Tag>> tagData = Optional.ofNullable(tagRepository.findByName(name));

        if (tagData.isPresent()) {
            return new ResponseEntity<>(tagData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }*/

    /*@GetMapping("/tags/name/{name}")
    public ResponseEntity<Optional<com.sup1x.api.model.Tag>> getTagByName(@PathVariable("name") String name) {
        Optional<Optional<com.sup1x.api.model.Tag>> tagData = Optional.ofNullable(tagRepository.findByName(name));

        if (tagData.isPresent()) {
            return new ResponseEntity<>(tagData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }*/

    @GetMapping("/tags/name/{name}")
    public ResponseEntity<?> getTagByName(@PathVariable("name") String name) {
        System.out.println("Searching for name: " + name);

        Optional<com.sup1x.api.model.Tag> tagData = tagRepository.findByName(name);

        System.out.println("Number of results: " + (tagData.isPresent() ? 1 : 0));

        if (tagData.isPresent()) {
            return new ResponseEntity<>(tagData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }





/*    @PostMapping("/tags/add")
    public ResponseEntity<com.sup1x.api.model.Tag> addTag(@RequestBody com.sup1x.api.model.Tag tag) {
        // Überprüfen, ob das Tag bereits in der Datenbank vorhanden ist
        Optional<com.sup1x.api.model.Tag> existingTagData = tagRepository.findById(tag.getId());

        if (existingTagData.isPresent()) {
            // Das Tag existiert bereits, daher Artikel zur Liste der Artikel im Tag hinzufügen
            com.sup1x.api.model.Tag existingTag = existingTagData.get();
            Collection<Object> articles = existingTag.getArticles();
            if (!articles.containsAll(tag.getArticles())) {
                articles.addAll(tag.getArticles());
            }

            // Das aktualisierte Tag-Objekt in der Datenbank speichern
            com.sup1x.api.model.Tag updatedTag = tagRepository.save(existingTag);

            return new ResponseEntity<>(updatedTag, HttpStatus.OK);
        } else {
            // Das Tag existiert noch nicht, daher neu hinzufügen
            com.sup1x.api.model.Tag _tag = tagRepository.save(
                    new com.sup1x.api.model.Tag(
                            tag.getId(),
                            tag.getName(),
                            tag.getArticles().size()
                    ));

            return new ResponseEntity<>(_tag, HttpStatus.CREATED);
        }
    }*/



    /*@PostMapping("/tags/add")
    public ResponseEntity<com.sup1x.api.model.Tag> addTag(@RequestBody com.sup1x.api.model.Tag tag) {
        com.sup1x.api.model.Tag _tag = tagRepository.save(
                new com.sup1x.api.model.Tag(
                        tag.getId(),
                        tag.getName(),
                        tag.getArticleIds()
                ));
        return new ResponseEntity<>(_tag, HttpStatus.CREATED);
    }*/

    /*@PostMapping("/tags/add")
    public ResponseEntity<com.sup1x.api.model.Tag> addTag(@RequestBody com.sup1x.api.model.Tag tag) {
        com.sup1x.api.model.Tag _tag = tagRepository.save(
                new com.sup1x.api.model.Tag(
                        tag.getId(),
                        tag.getName(),
                        tag.getArticleId()
                ));
        return new ResponseEntity<>(_tag, HttpStatus.CREATED);
    }*/

    @PostMapping("/tags/add")
    public ResponseEntity<?> addTag(@RequestBody com.sup1x.api.model.Tag tag) {
        // Check if a tag with the same name already exists
        Optional<com.sup1x.api.model.Tag> existingTag = tagRepository.findByName(tag.getName());

        if (existingTag.isPresent()) {
            String message = "Tag with name " + tag.getName() + " already exists.";
            return new ResponseEntity<>(message, HttpStatus.CONFLICT);
        }

        com.sup1x.api.model.Tag _tag = tagRepository.save(
                new com.sup1x.api.model.Tag(
                        tag.getId(),
                        tag.getName(),
                        tag.getArticleId()
                ));
        return new ResponseEntity<>(_tag, HttpStatus.CREATED);
    }

    /*@PostMapping("/tags/add")
    public ResponseEntity<?> addTag(@RequestBody com.sup1x.api.model.Tag tag) {
        // Überprüfen, ob das Tag bereits in der Datenbank vorhanden ist
        if (tagRepository.existsById(tag.getId())) {
            // Das Tag existiert bereits, daher Fehlermeldung ausgeben
            String errorMessage = "Tag mit ID " + tag.getId() + " existiert bereits.";
            return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
        }

        // Das Tag ist neu und kann zur Datenbank hinzugefügt werden
        com.sup1x.api.model.Tag _tag = tagRepository.save(
                new com.sup1x.api.model.Tag(
                        tag.getId(),
                        tag.getName(),
                        tag.getArticleId()
                ));
        return new ResponseEntity<>(_tag, HttpStatus.CREATED);
    }*/

    // update tag by id
/*    @PutMapping("/tags/update/id/{id}")
    public ResponseEntity<com.sup1x.api.model.Tag> updateTag(@PathVariable("id") long id, @RequestBody com.sup1x.api.model.Tag tag) {
        Optional<com.sup1x.api.model.Tag> tagData = tagRepository.findById(id);

        if (tagData.isPresent()) {
            com.sup1x.api.model.Tag existingTag = tagData.get();
            existingTag.setName(tag.getName());

            // Überprüfen, ob der Artikel bereits unter dem vorhandenen Tag gespeichert ist
            if (!existingTag.getArticles().contains(tag.getArticleId())) {
                // Artikel zur Liste der Artikel im Tag hinzufügen
                existingTag.getArticles().add(tag.getArticleId());
            }

            // Das aktualisierte Tag-Objekt in der Datenbank speichern
            com.sup1x.api.model.Tag updatedTag = tagRepository.save(existingTag);

            return new ResponseEntity<>(updatedTag, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }*/

    @PutMapping("/tags/update/id/{id}")
    public ResponseEntity<com.sup1x.api.model.Tag> updateTag(@PathVariable("id") long id, @RequestBody com.sup1x.api.model.Tag tag) {
        Optional<com.sup1x.api.model.Tag> tagData = tagRepository.findById(id);

        if (tagData.isPresent()) {
            com.sup1x.api.model.Tag _tag = tagData.get();
            _tag.setName(tag.getName());
//            _tag.setArticleId(tag.getArticleId());
            return new ResponseEntity<>(tagRepository.save(_tag), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

/*    @PutMapping("/tags/update/id/{id}")
    public ResponseEntity<?> updateTag(@PathVariable("id") long id, @RequestBody com.sup1x.api.model.Tag tag) {
        // Überprüfen, ob das Tag mit der angegebenen ID existiert
        Optional<com.sup1x.api.model.Tag> tagData = tagRepository.findById(id);

        if (tagData.isPresent()) {
            com.sup1x.api.model.Tag _tag = tagData.get();
            _tag.setName(tag.getName());
            // Hier kannst du auch andere Aktualisierungen am Tag vornehmen

            // Überprüfen, ob das aktualisierte Tag in der Datenbank bereits existiert
            if (tagRepository.existsByName(_tag.getName())) {
                // Das aktualisierte Tag existiert bereits, daher Fehlermeldung ausgeben
                String errorMessage = "Tag mit Name: " + _tag.getName() + " existiert bereits.";
                return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
            } else {
                // Das Tag aktualisieren und die Erfolgsantwort zurückgeben
                return new ResponseEntity<>(tagRepository.save(_tag), HttpStatus.OK);
            }
        } else {
            // Das Tag mit der angegebenen ID wurde nicht gefunden
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }*/


    // update tag by name
    @PutMapping("/tags/update/name/{name}")
    public ResponseEntity<com.sup1x.api.model.Tag> updateTag(@PathVariable("name") String name, @RequestBody com.sup1x.api.model.Tag tag) {
        Optional<com.sup1x.api.model.Tag> tagData = tagRepository.findByName(name);

        if (tagData.isPresent()) {
            com.sup1x.api.model.Tag _tag = tagData.get();
            String _tagname = tag.getName();
            _tag.setName(tag.getName());
//            _tag.setArticleId(tag.getArticleId());
            return new ResponseEntity<>(tagRepository.save(_tag), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // delete tag by id
    @DeleteMapping("/tags/delete/id/{id}")
    public ResponseEntity<HttpStatus> deleteTag(@PathVariable("id") long id) {
        try {
            tagRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    // delete tag by name (first find id of tag name and then delete by id)
    @DeleteMapping("/tags/delete/name/{name}")
    public ResponseEntity<HttpStatus> deleteTag(@PathVariable("name") String name) {
        try {
            Optional<com.sup1x.api.model.Tag> tagData = tagRepository.findByName(name);
            if (tagData.isPresent()) {
                tagRepository.deleteById(tagData.get().getId());
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    // delete all tags
    @DeleteMapping("/tags/delete/all")
    public ResponseEntity<HttpStatus> deleteAllTags() {
        try {
            tagRepository.deleteAll();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    // find all tags by article id
    @GetMapping("/tags/articleId/{articleId}")
    public ResponseEntity<List<com.sup1x.api.model.Tag>> findByArticleId(@PathVariable("articleId") int articleId) {
        try {
            List<com.sup1x.api.model.Tag> tags = tagRepository.findByArticleId(articleId);

            if (tags.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(tags, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

/*    // find all tags by article name
    @GetMapping("/find/{articleName}")
    public ResponseEntity<List<com.sup1x.api.model.Tag>> findByArticleName(@PathVariable("articleName") String articleName) {
        try {
            List<com.sup1x.api.model.Tag> tags = tagRepository.findByArticleName(articleName);

            if (tags.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(tags, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }*/

/*
    // find all tags by article slug
    @GetMapping("/find/{articleSlug}")
    public ResponseEntity<List<com.sup1x.api.model.Tag>> findByArticleSlug(@PathVariable("articleSlug") String articleSlug) {
        try {
            List<com.sup1x.api.model.Tag> tags = tagRepository.findByArticleSlug(articleSlug);

            if (tags.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(tags, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
*/

/*    // find all tags by article title
    @GetMapping("/find/{articleTitle}")
    public ResponseEntity<List<com.sup1x.api.model.Tag>> findByArticleTitle(@PathVariable("articleTitle") String articleTitle) {
        try {
            List<com.sup1x.api.model.Tag> tags = tagRepository.findByArticleTitle(articleTitle);

            if (tags.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(tags, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }*/

}
