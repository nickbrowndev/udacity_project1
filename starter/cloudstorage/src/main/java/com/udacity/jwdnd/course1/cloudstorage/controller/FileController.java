package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.exception.EntityNotFoundException;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public String listUploadedFiles(Model model, Authentication authentication) {

        model.addAttribute("files", fileService.getFilesForUser(authentication).stream().map(
                        path -> MvcUriComponentsBuilder.fromMethodName(FileController.class,
                                "serveFile", path.getFileName()).build().toUri().toString())
                .collect(Collectors.toList()));

        return "home";
    }

    @GetMapping("/files/{fileId}")
    @ResponseBody
    public ResponseEntity<byte[]> serveFile(@PathVariable Integer fileId, RedirectAttributes redirectAttributes, Authentication authentication) {
            ;
        File file;
        try {
            file = fileService.getFile(fileId, authentication);
            return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                            "attachment; filename=\"" + file.getFileName() + "\"")
                    .header(HttpHeaders.CONTENT_TYPE, file.getContentType())
                    .header(HttpHeaders.CONTENT_LENGTH, String.valueOf(file.getFileSize()))
                    .body(file.getFileData());
        } catch (EntityNotFoundException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Could not find file for user");
        }
        return ResponseEntity.badRequest().body(null);
    }

    @PostMapping("/files")
    public String handleFileUpload(@RequestParam("fileUpload") MultipartFile file,
                                   RedirectAttributes redirectAttributes,
                                   Authentication authentication) throws IOException {
        byte[] fileData = file.getInputStream().readAllBytes();
        File newFile = new File(null, userService.getCurrentUser(authentication), file.getOriginalFilename(), file.getContentType(), fileData.length, fileData);

        if (!fileService.fileExists(newFile, authentication)) {
            fileService.insert(newFile, authentication);
            redirectAttributes.addFlashAttribute("successMessage",
                    "You successfully uploaded " + file.getOriginalFilename() + "!");
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "File '" + file.getOriginalFilename() + "' already exists");
        }
        return "redirect:/home";
    }

    @PostMapping("/files/{fileId}/actions/delete")
    public String deleteFile(@PathVariable("fileId") Integer fileId, RedirectAttributes redirectAttributes) {
        fileService.deleteFile(fileId);
        redirectAttributes.addFlashAttribute("successMessage",
                "File deleted!");
        return "redirect:/home";
    }
}
