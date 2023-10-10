package com.sup1x.api.controller;

import java.util.List;
import java.util.stream.Collectors;

import com.sup1x.api.model.File;
import com.sup1x.api.repository.FileRepository;
import com.sup1x.api.repository.UserRepository;
import io.swagger.v3.oas.annotations.tags.Tag;
//import org.apache.tomcat.jni.FileInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.sup1x.api.service.FileStorageService;
import com.sup1x.api.payload.response.ResponseFile;
import com.sup1x.api.payload.response.ResponseMessage;
import com.sup1x.api.model.File;

@Tag(name = "Files V1", description = "Files V1 Management API")
@RestController
@RequestMapping("/api/v1/files")
//@CrossOrigin(origins = "*", maxAge = 3600)
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600, allowCredentials="true")
public class FileController {

    @Autowired
    private FileStorageService storageService;

    @PostMapping("/upload")
    public ResponseEntity<ResponseMessage> uploadFile(@RequestParam("file") MultipartFile file) {
        String message = "";

        // Get the authentication object from the SecurityContextHolder
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated() && authentication.getPrincipal() instanceof UserDetails userDetails) {
            String username = userDetails.getUsername();

            try {
                storageService.store(file);

                message = "Uploaded the file successfully: " + file.getOriginalFilename() + " -> Authorized by user: " + username;
                return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
            } catch (Exception e) {
                message = "Could not upload the file: " + file.getOriginalFilename() + "!";
                return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
            }
        }

        message = "User not authenticated.";
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ResponseMessage(message));
    }



    @GetMapping("/all")
    public ResponseEntity<List<ResponseFile>> getListFiles() {
        List<ResponseFile> files = storageService.getAllFiles().map(dbFile -> {
            String fileDownloadUri = ServletUriComponentsBuilder
                    .fromCurrentContextPath()
                    .path("/files/")
                    .path(dbFile.getId())
                    .toUriString();

            return new ResponseFile(
                    dbFile.getName(),
                    fileDownloadUri,
                    dbFile.getType(),
                    dbFile.getData().length,
                    dbFile.getUsername());
        }).collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(files);
    }

    @GetMapping("/{id}")
    public ResponseEntity<byte[]> getFile(@PathVariable String id) {
        File file = storageService.getFile(id);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getName() + "\"")
                .body(file.getData());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ResponseMessage> deleteFile(@PathVariable String id) {
        String message = "";
        try {
            storageService.deleteFile(id);

            message = "Deleted the file successfully: " + id;
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
        } catch (Exception e) {
            message = "Could not delete the file: " + id + "!";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
        }
    }

    @GetMapping("/all/{username}")
    public ResponseEntity<List<ResponseFile>> getListFilesByUsername(@PathVariable String username) {
        List<ResponseFile> files = storageService.getAllFiles()
                .filter(dbFile -> dbFile.getUsername().equals(username))
                .map(dbFile -> {
                    String fileDownloadUri = ServletUriComponentsBuilder
                            .fromCurrentContextPath()
                            .path("/files/")
                            .path(dbFile.getId())
                            .toUriString();

                    return new ResponseFile(
                            dbFile.getName(),
                            fileDownloadUri,
                            dbFile.getType(),
                            dbFile.getData().length,
                            dbFile.getUsername());
                })
                .collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(files);
    }

    @GetMapping("{username}/{id}")
    public ResponseEntity<byte[]> getFileByUsername(@PathVariable String username, @PathVariable String id) {
        File file = storageService.getFile(id);

        if (file.getUsername().equals(username)) {
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getName() + "\"")
                    .body(file.getData());
        }

        return null;
    }

    @GetMapping("/all/{username}/{name}")
    public ResponseEntity<List<ResponseFile>> getListFilesByUsernameContaining(@PathVariable String username, @PathVariable String name) {
        List<ResponseFile> files = storageService.getAllFiles()
                .filter(dbFile -> dbFile.getUsername().equals(username))
                .filter(dbFile -> dbFile.getName().contains(name))
                .map(dbFile -> {
                    String fileDownloadUri = ServletUriComponentsBuilder
                            .fromCurrentContextPath()
                            .path("/files/")
                            .path(dbFile.getId())
                            .toUriString();

                    return new ResponseFile(
                            dbFile.getName(),
                            fileDownloadUri,
                            dbFile.getType(),
                            dbFile.getData().length,
                            dbFile.getUsername());
                })
                .collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(files);
    }

    @GetMapping("/all/{username}/{type}")
    public ResponseEntity<List<ResponseFile>> getListFilesByTypeContaining(@PathVariable String username, @PathVariable String type) {
        List<ResponseFile> files = storageService.getAllFiles()
                .filter(dbFile -> dbFile.getUsername().equals(username))
                .filter(dbFile -> dbFile.getType().contains(type))
                .map(dbFile -> {
                    String fileDownloadUri = ServletUriComponentsBuilder
                            .fromCurrentContextPath()
                            .path("/files/")
                            .path(dbFile.getId())
                            .toUriString();

                    return new ResponseFile(
                            dbFile.getName(),
                            fileDownloadUri,
                            dbFile.getType(),
                            dbFile.getData().length,
                            dbFile.getUsername());
                })
                .collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(files);
    }

    @GetMapping("/all/{username}/{name}/{type}")
    public ResponseEntity<List<ResponseFile>> getListFilesByNameAndTypeContaining(@PathVariable String username, @PathVariable String name, @PathVariable String type) {
        List<ResponseFile> files = storageService.getAllFiles()
                .filter(dbFile -> dbFile.getUsername().equals(username))
                .filter(dbFile -> dbFile.getName().contains(name))
                .filter(dbFile -> dbFile.getType().contains(type))
                .map(dbFile -> {
                    String fileDownloadUri = ServletUriComponentsBuilder
                            .fromCurrentContextPath()
                            .path("/files/")
                            .path(dbFile.getId())
                            .toUriString();

                    return new ResponseFile(
                            dbFile.getName(),
                            fileDownloadUri,
                            dbFile.getType(),
                            dbFile.getData().length,
                            dbFile.getUsername());
                })
                .collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(files);
    }

}
