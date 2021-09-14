package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/home")
public class HomeController {
    private final FileService fileService;
    private final UserService userService;
    private final NoteService noteService;
    private final CredentialService credentialService;

    public HomeController(UserService userService, NoteService noteService, FileService fileService, CredentialService credentialService) {
        this.userService = userService;
        this.noteService = noteService;
        this.fileService = fileService;
        this.credentialService = credentialService;
    }

    @RequestMapping()
    public String getHome(@ModelAttribute @RequestParam(name = "noteid",required = false) String noteid,
                          @ModelAttribute @RequestParam(name = "fileid", required = false) String fileid,
                          @ModelAttribute @RequestParam(name = "credentialId", required = false) String credentialId,
                          Authentication authentication,
                          Model model) {
        User user = userService.getUserByUsername(authentication.getName());
        if(noteid != null)
        {
            noteService.createNote(new Note(null, (String)model.getAttribute("notetitle"), (String)model.getAttribute("notedescription"), user.getUserid()));
            System.out.println("WasSet: " + noteid);
        }

        if(credentialId != null)
        {
            credentialService.createCredential(new Credential(null, (String)model.getAttribute("url"), (String)model.getAttribute("username"), "key", (String)model.getAttribute("password"), user.getUserid()));
            System.out.println("WasSet: " + credentialId);
        }

        if(fileid != null)
        {
            fileService.createFile(new File(null, (String)"", (String)"", "", user.getUserid(), new byte[1]));
            System.out.println("WasSet: " + fileid);
        }

        User u = userService.getUserByUsername(authentication.getName());
        List<Note> notes = noteService.getNotesByUser(u);
        model.addAttribute("notes", notes);

        List<File> files = fileService.getFilesByUser(u);
        model.addAttribute("files", files);

        List<Credential> creds = credentialService.getCredentialsByUser(u);
        model.addAttribute("credentials", creds);

        model.addAttribute("credential", new Credential(null, null, null, null, null, null));
        model.addAttribute("file", new File(null, null, null, null, null, null));
        model.addAttribute("note", new Note(null, null, null, null));
        return "home";
    }
}