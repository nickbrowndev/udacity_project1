package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;
import java.util.Objects;

@Service
public class NoteService {

    private final NoteMapper noteMapper;

    public NoteService(NoteMapper noteMapper) {
        this.noteMapper = Objects.requireNonNull(noteMapper);
    }

    public int createNote(Note note, User user) {
        return noteMapper.insert(new Note(null, user, note.getNoteTitle(), note.getNoteDescription()));
    }

    public List<Note> getNotesForUser(User user) {
        return noteMapper.getNotesForUser(user);
    }

    public void deleteNote(Integer noteId) {
        noteMapper.delete(noteId);
    }
}
