package com.udacity.jwdnd.course1.cloudstorage.model;

public class Note {

    private Integer noteId;
    private User user;
    private String noteTitle;
    private String noteDescription;

    public Note() {

    }

    public Note(Integer noteId, User user, String noteTitle, String noteDescription) {
        setNoteId(noteId);
        setUser(user);
        setNoteTitle(noteTitle);
        setNoteDescription(noteDescription);
    }

    public Integer getNoteId() {
        return noteId;
    }

    public void setNoteId(Integer noteId) {
        this.noteId = noteId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getNoteTitle() {
        return noteTitle;
    }

    public void setNoteTitle(String noteTitle) {
        this.noteTitle = noteTitle;
    }

    public String getNoteDescription() {
        return noteDescription;
    }

    public void setNoteDescription(String noteDescription) {
        this.noteDescription = noteDescription;
    }

}
