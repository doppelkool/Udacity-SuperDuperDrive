package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.NoteForm;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;

@Service
public class NoteService {
    private NoteMapper noteMapper;
    private UserMapper userMapper;
    private User currentUser;
    private Authentication authentication;


    public NoteService(NoteMapper noteMapper, UserMapper userMapper) {
        this.noteMapper = noteMapper;
        this.userMapper = userMapper;
    }

    public void addNote(NoteForm NoteForm, Authentication authentication){
        User u = userMapper.getUserByUName(authentication.getName());
        currentUser = userMapper.getUser(u.getUserid());
        Note note = new Note();
        note.setUserid(currentUser.getUserid());
        note.setNotetitle(NoteForm.getNoteTitle());
        note.setNotedescription(NoteForm.getNoteDescription());
        int num_notes = noteMapper.insertNote(note);
        System.out.println(num_notes + " notes added");
        System.out.println("userid on note: " + note.getUserid());
        System.out.println("noteid on note: " + note.getNoteid());
    }

    public List<Note> getAllNotes(Authentication authentication){
        User u = userMapper.getUserByUName(authentication.getName());
        currentUser = userMapper.getUser(u.getUserid());
        return noteMapper.getNotesByUserId(u.getUserid());
    }

    public Note getNote(String notetitle){
        return noteMapper.getNoteByTitle(notetitle);
    }

    public Note getNoteById(Integer noteid){
        return noteMapper.getNoteByID(noteid);
    }

    public void deleteNote(Integer userid, Integer noteid){
        Integer userById = userMapper.getUser(userid).getUserid();
        Integer noteById = noteMapper.getNoteByID(noteid).getNoteid();
        if(userById.equals(noteMapper.getNoteByID(noteid).getUserid())){
            noteMapper.deleteNote(noteById);
        } else{
            throw new RuntimeException("There was an error while processing this request...");
        }
    }

    public void updateNote(String newTitle, String description, String title){
        Note note = noteMapper.getNoteByTitle(title);
        Integer noteId = note.getNoteid();
        int numRows = noteMapper.updateCred(noteId, newTitle, description);
        System.out.println(numRows + " rows updated");
        System.out.println("Successfully updated note with title: " + title + " and noteid : " + noteId);
        System.out.println("New title: " + newTitle + "\nNew description : " + description);
    }

    public void updateNoteById(String newTitle, String description, Integer noteid){
        String titleBeforeChange = noteMapper.getNoteByID(noteid).getNotetitle();
        System.out.println("Updating note with id: " + noteid + " and title : " + titleBeforeChange);
        int rowsChanged = noteMapper.updateCred(noteid, newTitle, description);
        System.out.println("Note successfully updated \n" + rowsChanged + " rows updated\n" +
                "New title: " + newTitle + "\nNew description: " + description);
    }

}
