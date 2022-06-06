package com.udacity.jwdnd.course1.cloudstorage.controller;


import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/home")
public class HomeController {

    private static final Logger log = LoggerFactory.getLogger(HomeController.class);
    private UserService userService;
    private NoteService noteService;
    public HomeController(UserService userService, NoteService noteService) {
        this.userService = Objects.requireNonNull(userService);
        this.noteService = Objects.requireNonNull(noteService);
    }

    @GetMapping
    public String getHomePage(@ModelAttribute("note") Note note, Model model, Authentication authentication) {

        User currentUser = getCurrentUser(authentication);
        model.addAttribute("notes", noteService.getNotesForUser(currentUser));
        return "home";
    }

    @PostMapping("/note")
    public String postNote(@ModelAttribute("note") Note note, Model model, Authentication authentication) {
        User currentUser = getCurrentUser(authentication);
        noteService.createNote(note, currentUser);
        model.addAttribute("notes", noteService.getNotesForUser(currentUser));
        return "home";
    }

    @PostMapping("/note/{noteId}/actions/delete")
    public String deleteNote(@PathVariable("noteId") String noteId) {
        noteService.deleteNote(Integer.valueOf(noteId));
        return "home";
    }

    @ModelAttribute
    public List<Note> getNotes(Authentication authentication) {
        User currentUser = getCurrentUser(authentication);
        return noteService.getNotesForUser(currentUser);
    }

    private User getCurrentUser(Authentication authentication) {
        String username = authentication.getName();
        User currentUser = userService.getUserByUsername(username);

        return currentUser;
    }


}

