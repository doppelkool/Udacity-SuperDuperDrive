package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FileService {
    private final FileMapper fileMapper;

    public FileService(FileMapper fileMapper)
    {
        this.fileMapper = fileMapper;
    }

    public boolean isNoteAvailable(Integer fileId) {
        return fileMapper.getFile(fileId) != null;
    }

    public int createFile(File file) {
        return fileMapper.insertFile(file);
    }

    public File getFileByID(Integer fileId) {
        return fileMapper.getFile(fileId);
    }

    public List<File> getFilesByUser(User user) {
        return fileMapper.getFilesByUser(user.getUserid());
    }
}
