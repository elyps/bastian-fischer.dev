package com.sup1x.api.controller;

import com.sup1x.api.exception.ResourceNotFoundException;
import com.sup1x.api.model.Project;
import com.sup1x.api.repository.ProjectRepository;
import com.sup1x.api.repository.TagRepository;
import com.sup1x.api.service.ProjectService;
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
@CrossOrigin(origins = "http://localhost:8083", maxAge = 3600, allowCredentials="true")
@Tag(name = "Project V1", description = "Project V1 Management API")
@RestController
@RequestMapping("/api/v1/projects")
public class ProjectController {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private ProjectService projectService;

    private final NotificationService notificationService;

    @Autowired
    private TagRepository tagRepository;

    // @Autowired
    // private CommentRepository commentRepository;

    @Autowired
    public ProjectController(ProjectService projectService, NotificationService notificationService, TagRepository tagRepository) {
        this.projectService = projectService;
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

    @GetMapping("/sortedprojects")
    public ResponseEntity<List<Project>> getAllProjects(@RequestParam(defaultValue = "id,desc") String[] sort) {

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

            List<Project> projects = projectRepository.findAll(Sort.by(orders));

            if (projects.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(projects, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /*@GetMapping("/all")
    public ResponseEntity<List<Project>> getAllprojects(@RequestParam(required = false) String title) {
        try {
            List<Project> projects;
            if (title == null) {
                projects = projectRepository.findAll();
            } else {
                projects = projectRepository.findByTitleContaining(title);
            }
            if (projects.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(projects);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }*/

    /*@GetMapping("/all")
    public ResponseEntity<List<Project>> getAllprojectsWithTags() {
        List<Project> projects = projectRepository.findAll();
        return ResponseEntity.ok(projects);
    }*/


    @GetMapping("/all")
    public ResponseEntity<Map<String, Object>> getAllProjectsPage(
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

            List<Project> projects = new ArrayList<Project>();
            Pageable pagingSort = PageRequest.of(page, size, Sort.by(orders));

            Page<Project> pageArts;
            if (title == null)
                pageArts = projectRepository.findAll(pagingSort);
            else
                pageArts = projectRepository.findByTitleContaining(title, pagingSort);

            projects = pageArts.getContent();

            Map<String, Object> response = new HashMap<>();
            response.put("projects", projects);
            response.put("currentPage", pageArts.getNumber());
            response.put("totalItems", pageArts.getTotalElements());
            response.put("totalPages", pageArts.getTotalPages());

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /*@GetMapping("/{id}")
    public ResponseEntity<Project> getprojectById(@PathVariable(value = "id") int id) {
        try {
            project project = projectRepository.findById((long) id).orElseThrow(() -> new Exception("project not found for this id :: " + id));
            return ResponseEntity.ok(project);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }*/

    @GetMapping("/{id}")
    public ResponseEntity<Project> getprojectById(@PathVariable("id") long id) {
        Optional<Project> projectData = projectRepository.findById(id);

        if (projectData.isPresent()) {
            return new ResponseEntity<>(projectData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/slug/{slug}")
    public ResponseEntity<Project> findProjectBySlug(@PathVariable("slug") String slug) {
        Optional<Project> projectData = projectRepository.findProjectBySlug(slug);

        if (projectData.isPresent()) {
            return new ResponseEntity<>(projectData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<Project> createproject(@RequestBody Project project) {
        // Get the authentication object from the SecurityContextHolder
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated() && authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String username = userDetails.getUsername();

            // Create a new project instance
            Project newproject = new Project(
                    (int) project.getId(),
                    project.getSlug(),
                    project.getTitle(),
                    project.getContent(),
                    project.getCategory(),
                    /* project.getTags(), */
                    // project.getImages(),
                    project.getDescription(),
                    project.getUrl(),
                    // project.getLanguages(),
                    project.getPublished(),
                    project.getCreatedAt(),
                    project.getUpdatedAt()
            );

            // Set the author to the logged-in user's username
            newproject.setAuthor(username);

            try {
                Project savedproject = projectRepository.save(newproject);
                notificationService.sendNotification("New project created: " + project.getTitle());

                return new ResponseEntity<>(savedproject, HttpStatus.CREATED);
            } catch (Exception e) {
                return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<Project> updateproject(@PathVariable("id") long id, @RequestBody Project project) {
        Optional<Project> projectData = projectRepository.findById(id);

        if (projectData.isPresent()) {
            Project _project = projectData.get();
            if (project.getTitle() != null) {
                _project.setTitle(project.getTitle());
            }

            if (project.getContent() != null) {
                _project.setContent(project.getContent());
            }

            if (project.getAuthor() != null) {
                _project.setAuthor(project.getAuthor());
            }

            if (project.getCategory() != null) {
                _project.setCategory(project.getCategory());
            }

//            if (project.getTags() != null) {
//                _project.setTags(project.getTags());
//            }

            // if (project.getImages() != null) {
            //     _project.setImages(project.getImages());
            // }

            if (project.getDescription() != null) {
                _project.setDescription(project.getDescription());
            }

            if (project.getUrl() != null) {
                _project.setUrl(project.getUrl());
            }

            // if (project.getLanguages() != null) {
            //     _project.setLanguages(project.getLanguages());
            // }

            // _project.setPublished(true);
            // project.setCreatedAt(LocalDate.now());
            _project.setUpdatedAt(LocalDate.now());
            return new ResponseEntity<>(projectRepository.save(_project), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<HttpStatus> deleteprojectById(@PathVariable(value = "id") int id) {
        try {
            projectRepository.deleteById((long) id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/delete/all")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<HttpStatus> deleteAllprojects() {
        try {
            projectRepository.deleteAll();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

/*    @GetMapping("/published")
    public ResponseEntity<List<Project>> findByPublished() {
        try {
            List<Project> projects = projectRepository.findByPublished(true);
            if (projects.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(projects);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }*/

    @GetMapping("/published")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<Map<String, Object>> findByPublished(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size) {

        try {
            List<Project> projects = new ArrayList<Project>();
            Pageable paging = PageRequest.of(page, size);

            Page<Project> pageArts = projectRepository.findByPublished(true, paging);
            projects = pageArts.getContent();

            Map<String, Object> response = new HashMap<>();
            response.put("projects", projects);
            response.put("currentPage", pageArts.getNumber());
            response.put("totalItems", pageArts.getTotalElements());
            response.put("totalPages", pageArts.getTotalPages());

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

   /* @GetMapping("/tags/all")
    public ResponseEntity<List<com.sup1x.api.model.Tag>> getAllTags() {
        List<com.sup1x.api.model.Tag> tags = tagRepository.findAll();
        return ResponseEntity.ok(tags);
    }*/

    /*@GetMapping("/{id}/tags")
    public ResponseEntity<List<com.sup1x.api.model.Tag>> getTagsForprojectAndTitle(
            @RequestParam("projectId") int projectId,
            @RequestParam("title") String title) {

        // Fügen Sie hier den Code hinzu, um Tags für den angegebenen Artikel und Titel abzurufen
        List<com.sup1x.api.model.Tag> tags = projectService.getTagsIn(projectId, title);

        if (tags.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(tags);
    }*/


    /*@GetMapping("/tags")
    public ResponseEntity<List<String>> getAllTags() {
        List<String> tags = tagRepository.findAllTags();
        return ResponseEntity.ok(tags);
    }*/

    /*@GetMapping("/tags")
    public ResponseEntity<List<Tag>> getAllTags() {
        List<Tag> tags = (List<Tag>) tagRepository.findAll();
        return ResponseEntity.ok(tags);
    }*/

    /*@GetMapping("/tags")
    public ResponseEntity<List<String>> findTagsByTags(@RequestParam(required = false) List<String> tags) {
        try {
            List<String> uniqueTags = new ArrayList<>();

            if (tags == null || tags.isEmpty()) {
                // Wenn keine Tags angegeben sind, alle eindeutigen Tags aus der Datenbank abrufen.
                List<Project> projects = projectRepository.findAll();
                for (project project : projects) {
                    uniqueTags.addAll(project.getTags());
                }
            } else {
                // Andernfalls Tags aus Artikeln mit den angegebenen Tags abrufen.
                List<Project> projects = projectRepository.findByTagsIn(tags);
                for (project project : projects) {
                    uniqueTags.addAll(project.getTags());
                }
            }

            // Die Liste der eindeutigen Tags erstellen (doppelte Tags entfernen).
            List<String> distinctTags = uniqueTags.stream()
                    .distinct()
                    .collect(Collectors.toList());

            if (distinctTags.isEmpty()) {
                return ResponseEntity.noContent().build();
            }

            return ResponseEntity.ok(distinctTags);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }*/



//    @GetMapping("/tags")
//    public ResponseEntity<List<Project>> findByTags(@RequestParam(required = false) List<String> tags) {
//        try {
//            List<Project> projects;
//            if (tags == null || tags.isEmpty()) {
//                projects = projectRepository.findAll();
//            } else {
//                projects = projectRepository.findByTagsIn(tags);
//            }
//            if (projects.isEmpty()) {
//                return ResponseEntity.noContent().build();
//            }
//            return ResponseEntity.ok(projects);
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//        }
//    }

/*     @GetMapping("/languages")
    public ResponseEntity<List<Project>> findByLanguages(@RequestParam(required = false) List<String> languages) {
        try {
            List<Project> projects;

            // Check if the "language" parameter is not provided in the request
            if (languages == null) {
                // If no language filter is given, retrieve all projects
                projects = projectRepository.findAll();
            } else {
                // If the "language" parameter is provided, fetch projects containing the specified language(s)
                projects = projectRepository.findByLanguagesIn(languages);
            }

            // If no projects are found, return a response with status code 204 (No Content)
            if (projects.isEmpty()) {
                return ResponseEntity.noContent().build();
            }

            // If projects are found, return a response with status code 200 (OK) and the list of projects
            return ResponseEntity.ok(projects);
        } catch (Exception e) {
            // If any exception occurs during processing, return a response with status code 404 (Not Found)
            return ResponseEntity.notFound().build();
        }
    } */

    @GetMapping("/category")
    public ResponseEntity<List<Project>> findByCategory(@RequestParam(required = false) String category) {
        try {
            List<Project> projects;
            if (category == null) {
                projects = projectRepository.findAll();
            } else {
                projects = projectRepository.findByCategoryContaining(category);
            }
            if (projects.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(projects);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/author")
    public ResponseEntity<List<Project>> findByAuthor(@RequestParam(required = false) String author) {
        try {
            List<Project> projects;
            if (author == null) {
                projects = projectRepository.findAll();
            } else {
                projects = projectRepository.findByAuthorContaining(author);
            }
            if (projects.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(projects);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/title")
    public ResponseEntity<List<Project>> findByTitle(@RequestParam(required = false) String title) {
        try {
            List<Project> projects;
            if (title == null) {
                projects = projectRepository.findAll();
            } else {
                projects = projectRepository.findByTitleContaining(title, Sort.by(Sort.Direction.DESC, "id"));
            }
            if (projects.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(projects);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/description")
    public ResponseEntity<List<Project>> findByDescription(@RequestParam(required = false) String description) {
        try {
            List<Project> projects;
            if (description == null) {
                projects = projectRepository.findAll();
            } else {
                projects = projectRepository.findByDescriptionContaining(description);
            }
            if (projects.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(projects);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/url")
    public ResponseEntity<List<Project>> findByUrl(@RequestParam(required = false) String url) {
        try {
            List<Project> projects;
            if (url == null) {
                projects = projectRepository.findAll();
            } else {
                projects = projectRepository.findByUrlContaining(url);
            }
            if (projects.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(projects);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/createdAt")
    public ResponseEntity<List<Project>> findByCreatedAt(@RequestParam(required = false) String createdAt) {
        try {
            List<Project> projects;
            if (createdAt == null) {
                projects = projectRepository.findAll();
            } else {
                projects = projectRepository.findByCreatedAtContaining(LocalDate.parse(createdAt));
            }
            if (projects.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(projects);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/updatedAt")
    public ResponseEntity<List<Project>> findByUpdatedAt(@RequestParam(required = false) String updatedAt) {
        try {
            List<Project> projects;
            if (updatedAt == null) {
                projects = projectRepository.findAll();
            } else {
                projects = projectRepository.findByUpdatedAtContaining(LocalDate.parse(updatedAt));
            }
            if (projects.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(projects);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    // @PostMapping("/{id}/like")
    // public void likeproject(@PathVariable Long id) {
    //     projectService.likeproject(id);
    // }

    // @PostMapping("/{id}/unlike")
    // public void unlikeproject(@PathVariable Long id) {
    //     projectService.unlikeproject(id);
    // }

    // @GetMapping("/{id}/likes")
    // public int getLikesCount(@PathVariable Long id) {
    //     return projectService.getLikesCount(id);
    // }

    // // Endpunkt zum Erstellen eines Kommentars für einen Beitrag
    // @PostMapping("/{id}/comments")
    // public Comment createComment(@PathVariable Long id, @RequestBody Comment comment) {

    //     Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    //     if (authentication != null && authentication.isAuthenticated() && authentication.getPrincipal() instanceof UserDetails) {
    //         UserDetails userDetails = (UserDetails) authentication.getPrincipal();
    //         String username = userDetails.getUsername();
    //         String createdAt = LocalDate.now() + " " + new Date().getHours() + ":" + new Date().getMinutes() + ":" + new Date().getSeconds();
    //         String updatedAt = LocalDate.now() + " " + new Date().getHours() + ":" + new Date().getMinutes() + ":" + new Date().getSeconds();

    //         return projectRepository.findById(id).map(project -> {
    //             comment.setproject(project);
    //             comment.setCommentAuthor(username); // Füge den Benutzernamen zum Kommentar hinzu
    //             comment.setCreatedAt(createdAt); // Füge das aktuelle Datum zum Kommentar hinzu
    //             comment.setUpdatedAt(updatedAt); // Füge das aktuelle Datum zum Kommentar hinzu
    //             Comment createdComment = commentRepository.save(comment);
    //             notificationService.sendNotification("New comment created!");
    //             return new ResponseEntity<>(createdComment, HttpStatus.CREATED);
    //         }).orElseThrow(() -> new ResourceNotFoundException("project not found with id " + id)).getBody();
    //     } else {
    //         return null;
    //     }

    //     /*project project = projectRepository.findById(id)
    //             .orElseThrow(() -> new ResourceNotFoundException("project not found with id " + id));
    //     comment.setproject(project);
    //     comment.setCommentAuthor(username); // Füge den Benutzernamen zum Kommentar hinzu
    //     comment.setCreatedAt(createdAt); // Füge das aktuelle Datum zum Kommentar hinzu
    //     comment.setUpdatedAt(updatedAt); // Füge das aktuelle Datum zum Kommentar hinzu
    //     return commentRepository.save(comment);*/
    // }

    // // Endpunkt zum Abrufen aller Kommentare für einen Beitrag
    // @GetMapping("/{id}/comments")
    // public List<Comment> getCommentsForPost(@PathVariable Long id) {
    //     return commentRepository.findByprojectId(id);
    // }

}
