package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.FileForm;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class FileService {
    private UserMapper userMapper;
    private FileMapper fileMapper;


    @Autowired
    public FileService(UserMapper userMapper, FileMapper fileMapper) {
        this.userMapper = userMapper;
        this.fileMapper = fileMapper;
    }

    public void addFile(FileForm FileForm, Authentication authentication) throws IOException {
        User u = userMapper.getUserByUName(authentication.getName());

        File file = new File();
        file.setFilename(FileForm.getFileUpload().getOriginalFilename());
        file.setContenttype(FileForm.getFileUpload().getContentType());
        file.setFilesize(String.valueOf(FileForm.getFileUpload().getSize()));
        file.setUserid(u.getUserid());
        file.setFiledata(FileForm.getFileUpload().getBytes());
        fileMapper.insertFile(file);
    }

    public boolean fileAlreadyExists(String filename){
        return fileMapper.getFileByName(filename) != null;
    }

    public File getFile(String fileName){
        return fileMapper.getFileByName(fileName);
    }

    public File getFileById(Integer fileId){
        return fileMapper.getFileByID(fileId);
    }

    public List<File> getAllFiles(Authentication authentication){
        return fileMapper.getFilesByUser(userMapper.getUserByUName(authentication.getName()).getUserid());
    }

    public void deleteFile(Integer userid, Integer fileid){
        if(userid.equals(fileMapper.getFileByID(fileid).getUserid())){
            fileMapper.deleteFile(fileid);
        } else{
            throw new RuntimeException("There was an error while processing this request...");
        }
    }

    public File downloadFile(Integer fileId){
        return fileMapper.getFileByID(fileId);
    }
}
