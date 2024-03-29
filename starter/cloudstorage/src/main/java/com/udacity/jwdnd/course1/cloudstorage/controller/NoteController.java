package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Objects;

@Controller
public class NoteController {
    private final NoteService noteService;
    private final UserService userService;

    public NoteController(UserService userService, NoteService noteService) {
        this.noteService = Objects.requireNonNull(noteService);
        this.userService = Objects.requireNonNull(userService);
    }

    @PostMapping("/notes")
    public String postNote(@ModelAttribute("note") Note note, Model model, Authentication authentication, RedirectAttributes redirectAttributes) {
        User currentUser = userService.getCurrentUser(authentication);
        if (note.getNoteId() != null) {
            noteService.updateNote(note, currentUser);
            redirectAttributes.addFlashAttribute("successMessage",
                    "Note updated!");
        } else {
            noteService.createNote(note, currentUser);
            redirectAttributes.addFlashAttribute("successMessage",
                    "Note created!");
        }
        return "redirect:/home";
    }

    @PostMapping("/notes/{noteId}/actions/delete")
    public String deleteNote(@PathVariable("noteId") String noteId) {
        noteService.deleteNote(Integer.valueOf(noteId));
        return "redirect:/home";
    }
}
