package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;

@Service
public class NoteService {
    private final NoteMapper noteMapper;

    public NoteService(NoteMapper noteMapper)
    {
        this.noteMapper = noteMapper;
    }

    public boolean isNoteAvailable(Integer noteid) {
        return noteMapper.getNoteByID(noteid) != null;
    }

    public int createNote(Note note) {
        return noteMapper.insertNote(note);
    }

    public List<Note> getNotesByUser(User user) {
        return noteMapper.getNotesByUserId(user.getUserid());
    }
}
