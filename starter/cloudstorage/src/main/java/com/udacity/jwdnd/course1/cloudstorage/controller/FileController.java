package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.apache.catalina.authenticator.SpnegoAuthenticator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.Objects;
import java.util.stream.Collectors;


@Controller("/file")
public class FileController {

    private final FileService fileService;
    private final UserService userService;
    @Autowired
    public FileController(FileService fileService, UserService userService) {
        this.fileService = Objects.requireNonNull(fileService);
        this.userService = Objects.requireNonNull(userService);
    }

    @GetMapping("/files")
    public String listUploadedFiles(Model model, Authentication authentication) throws IOException {

        model.addAttribute("files", fileService.getFilesForUser(authentication).stream().map(
                        path -> MvcUriComponentsBuilder.fromMethodName(FileController.class,
                                "serveFile", path.getFileName().toString()).build().toUri().toString())
                .collect(Collectors.toList()));

        return "home";
    }

    @GetMapping("/files/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {

       // Resource file = storageService.loadAsResource(filename);
        //return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
        //        "attachment; filename=\"" + file.getFilename() + "\"").body(file);
        return ResponseEntity.unprocessableEntity().build();
    }

    @PostMapping("/files")
    public String handleFileUpload(@RequestParam("fileUpload") MultipartFile file,
                                   RedirectAttributes redirectAttributes,
                                   Authentication authentication) throws IOException {
        byte[] fileData = file.getInputStream().readAllBytes();
        File newFile = new File(null, userService.getCurrentUser(authentication), file.getOriginalFilename(), file.getContentType(), fileData.length, fileData);

        fileService.insert(newFile, authentication);
        redirectAttributes.addFlashAttribute("message",
                "You successfully uploaded " + file.getOriginalFilename() + "!");

        return "redirect:/";
    }

//    @ExceptionHandler(StorageFileNotFoundException.class)
//    public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
//        return ResponseEntity.notFound().build();
//    }
}
