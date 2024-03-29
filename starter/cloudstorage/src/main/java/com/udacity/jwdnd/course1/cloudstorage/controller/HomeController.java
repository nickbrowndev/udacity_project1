package com.udacity.jwdnd.course1.cloudstorage.controller;


import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/home")
public class HomeController {

    private static final Logger log = LoggerFactory.getLogger(HomeController.class);
    private final UserService userService;
    private final NoteService noteService;
    private final FileService fileService;
    private final CredentialService credentialService;

    public HomeController(UserService userService, NoteService noteService, FileService fileService, CredentialService credentialService) {
        this.userService = Objects.requireNonNull(userService);
        this.noteService = Objects.requireNonNull(noteService);
        this.fileService = Objects.requireNonNull(fileService);
        this.credentialService = Objects.requireNonNull(credentialService);
    }

    @GetMapping
    public String getHomePage(@ModelAttribute("note") Note note, Model model, Authentication authentication) {

        User currentUser = userService.getCurrentUser(authentication);
        model.addAttribute("files", fileService.getFilesForUser(authentication));
        model.addAttribute("notes", noteService.getNotesForUser(currentUser));
        model.addAttribute("credentials", credentialService.getCredentialsForUser(authentication));
        return "home";
    }
    public String getNotePage(@ModelAttribute("note") Note note, Model model, Authentication authentication) {

        User currentUser = userService.getCurrentUser(authentication);
        model.addAttribute("notes", noteService.getNotesForUser(currentUser));
        return "home";
    }



    @ModelAttribute
    public List<Note> getNotes(Authentication authentication) {
        User currentUser = userService.getCurrentUser(authentication);
        return noteService.getNotesForUser(currentUser);
    }

    @ModelAttribute
    public List<File> getFiles(Authentication authentication) {
        return fileService.getFilesForUser(authentication);
    }




}

