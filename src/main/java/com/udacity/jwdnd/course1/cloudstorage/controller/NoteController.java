package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.*;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/note")
public class NoteController {
    private final FileService fileService;
    private final UserService userService;
    private final NoteService noteService;
    private final CredentialService credentialService;
    private final EncryptionService encryptionService;

    public NoteController(UserService userService, NoteService noteService, FileService fileService, CredentialService credentialService, EncryptionService encryptionService) {
        this.userService = userService;
        this.noteService = noteService;
        this.fileService = fileService;
        this.credentialService = credentialService;
        this.encryptionService = encryptionService;
    }

    @GetMapping
    public String getHome(Authentication authentication, Model model) {
        User u = userService.getUserByUsername(authentication.getName());

        model.addAttribute("notes", noteService.getNotesByUser(u));
        model.addAttribute("files", fileService.getFilesByUser(u));
        model.addAttribute("credentials", credentialService.getCredentialsByUser(u));

        model.addAttribute("credential", new Credential(null, null, null, null, null, null));
        model.addAttribute("file", new File(null, null, null, null, null, null));
        model.addAttribute("note", new Note(null, null, null, null));

        model.addAttribute("encryptionService",encryptionService);

        System.out.println("NoteController: Get");
        return "home";
    }

    @PostMapping
    public String getHome(@ModelAttribute("note") Note note,
                          Model model,
                          Authentication authentication) {

        Integer uid = userService.getUserByUsername(authentication.getName()).getUserid();
        noteService.createNote(new Note(null, note.getNotetitle(), note.getNotedescription(), uid));

        System.out.println("NoteController: Post");
        return getHome(authentication, model);
    }
}
