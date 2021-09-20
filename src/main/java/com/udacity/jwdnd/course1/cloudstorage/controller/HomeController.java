package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.*;
import com.udacity.jwdnd.course1.cloudstorage.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.sql.SQLException;

@Controller
public class HomeController {
    private final FileService fileService;
    private final UserService userService;
    private final NoteService noteService;
    private final CredentialService credentialService;
    private final EncryptionService encryptionService;
    
    private String successNote;

    @Autowired
    public HomeController(UserService userService, NoteService noteService, FileService fileService, CredentialService credentialService, EncryptionService encryptionService) {
        this.userService = userService;
        this.noteService = noteService;
        this.fileService = fileService;
        this.credentialService = credentialService;
        this.encryptionService = encryptionService;
    }

    @GetMapping("/home")
    public String getHome(@ModelAttribute("noteForm") NoteForm noteForm,
                          @ModelAttribute("fileForm") FileForm fileForm,
                          @ModelAttribute("credentialForm") CredentialForm credentialForm,
                          Authentication authentication, Model model) {
        User u = userService.getUserByUsername(authentication.getName());

        model.addAttribute("notes", noteService.getAllNotes(authentication));
        model.addAttribute("files", fileService.getAllFiles(authentication));
        model.addAttribute("credentials", credentialService.getCredentialsByUser(u));

        //model.addAttribute("credential", new Credential(null, null, null, null, null, null));
        //model.addAttribute("file", new File(null, null, null, null, null, null));
        //model.addAttribute("note", new Note(null, null, null, null));

        model.addAttribute("encryptionService",encryptionService);
        return "home";
    }

    @PostMapping("/add-note-result")
    public String writeNote(@ModelAttribute("NoteForm") NoteForm NoteForm, @ModelAttribute("FileForm") FileForm FileForm,
                            @ModelAttribute("CredentialForm") CredentialForm CredentialForm,
                            Model model, Authentication authentication){
        User u = userService.getUserByUsername(authentication.getName());

        noteService.addNote(NoteForm, authentication);

        model.addAttribute("notes", noteService.getAllNotes(authentication));
        model.addAttribute("files", fileService.getAllFiles(authentication));
        model.addAttribute("credentials", credentialService.getCredentialsByUser(u));
        
        model.addAttribute("encryptionService",encryptionService);
        
        successNote = "Note was successfully added.";
        model.addAttribute("successNote",successNote);
        return "result";
    }


    @GetMapping("/delete-note-result")
    public String deleteCurrentNote(@RequestParam(value = "noteid") Integer noteid, Model model,
                                    @ModelAttribute("FileForm") FileForm FileForm,@ModelAttribute("CredentialForm") CredentialForm CredentialForm,
                                    @ModelAttribute("NoteForm") NoteForm NoteForm, Authentication authentication){
        //Get data
        User u = userService.getUserByUsername(authentication.getName());

        //Manipulate Data
        Note note = noteService.getNoteById(noteid);
        noteService.deleteNote(u.getUserid(), note.getNoteid());

        //Send data to frontend
        model.addAttribute("notes", noteService.getAllNotes(authentication));
        model.addAttribute("files", fileService.getAllFiles(authentication));
        model.addAttribute("credentials", credentialService.getCredentialsByUser(u));
        
        model.addAttribute("encryptionService",encryptionService);


        successNote = "Note was successfully deleted.";
        model.addAttribute("successNote",successNote);
        return "result";
    }

    @PostMapping("/update-note-result")
    public String updateNote(@RequestParam("noteId") Integer noteId, Model model, @ModelAttribute("FileForm") FileForm FileForm,
                             @ModelAttribute("CredentialForm") CredentialForm CredentialForm,
                             @ModelAttribute("NoteForm") NoteForm NoteForm,Authentication authentication){
        //Get data
        User u = userService.getUserByUsername(authentication.getName());

        //Manipulate Data
        noteService.updateNoteById(NoteForm.getNoteTitle(), NoteForm.getNoteDescription(), noteId);

        //Send data to frontend
        model.addAttribute("notes", noteService.getAllNotes(authentication));
        model.addAttribute("credentials", credentialService.getCredentialsByUser(u));
        model.addAttribute("files", fileService.getAllFiles(authentication));

        model.addAttribute("encryptionService",encryptionService);

        successNote = "Note was successfully updated.";
        model.addAttribute("successNote",successNote);

        return "result";
    }

    @PostMapping("/add-credential-result")
    public String addCredential(@ModelAttribute("NoteForm") NoteForm NoteForm, @ModelAttribute("FileForm") FileForm FileForm,
                                @ModelAttribute("CredentialForm") CredentialForm CredentialForm,
                                Model model, Authentication authentication){
        //Get data
        User u = userService.getUserByUsername(authentication.getName());

        //Manipulate Data
        credentialService.addCredential(CredentialForm, authentication);

        //Send data to frontend
        model.addAttribute("notes", noteService.getAllNotes(authentication));
        model.addAttribute("credentials", credentialService.getCredentialsByUser(u));
        model.addAttribute("files", fileService.getAllFiles(authentication));

        model.addAttribute("encryptionService",encryptionService);

        successNote = "Credential was successfully added.";
        model.addAttribute("successNote",successNote);

        return "result";
    }

    @GetMapping("/delete-credential-result")
    public String deleteCredential(@RequestParam(value = "id") Integer id, @RequestParam(value = "credentialId") Integer credentialId,@ModelAttribute("NoteForm") NoteForm NoteForm,
                                   @ModelAttribute("FileForm") FileForm FileForm,@ModelAttribute("CredentialForm") CredentialForm CredentialForm,
                                   Model model, Authentication authentication){
        //Get data
        User u = userService.getUserByUsername(authentication.getName());

        //Manipulate Data
        credentialService.deleteCredential(u.getUserid(),credentialId);

        //Send data to frontend
        model.addAttribute("notes", noteService.getAllNotes(authentication));
        model.addAttribute("credentials", credentialService.getCredentialsByUser(u));
        model.addAttribute("files", fileService.getAllFiles(authentication));

        model.addAttribute("encryptionService",encryptionService);

        successNote = "Credential was deleted successfully.";
        model.addAttribute("successNote",successNote);
        return "result";
    }

    @PostMapping("/update-credential-result")
    public String updateCredential(@RequestParam(value = "credentialId") Integer credentialId,@ModelAttribute("NoteForm") NoteForm NoteForm,
                                   @ModelAttribute("FileForm") FileForm FileForm,@ModelAttribute("CredentialForm") CredentialForm CredentialForm,
                                   Model model, Authentication authentication){
        //Get data
        User u = userService.getUserByUsername(authentication.getName());

        //Manipulate Data
        credentialService.updateCredential(CredentialForm.getCredentialUrl(),CredentialForm.getCredentialUsername(), CredentialForm.getCredentialPassword(),credentialId);

        //Send data to frontend
        model.addAttribute("notes", noteService.getAllNotes(authentication));
        model.addAttribute("credentials", credentialService.getCredentialsByUser(u));
        model.addAttribute("files", fileService.getAllFiles(authentication));

        model.addAttribute("encryptionService",encryptionService);

        successNote = "Credential was successfully updated.";
        model.addAttribute("successNote",successNote);
        return "result";
    }

    @PostMapping("/add-file-result")
    public String uploadFile(@ModelAttribute("FileForm") FileForm FileForm, @ModelAttribute("NoteForm") NoteForm NoteForm,
                             @ModelAttribute("CredentialForm") CredentialForm CredentialForm,
                             Model model, Authentication authentication) throws IOException, SQLException {
        //Get data
        User u = userService.getUserByUsername(authentication.getName());

        String uploadError = null;
        successNote = "File has been successfully uploaded.";
        if(fileService.fileAlreadyExists(FileForm.getFileUpload().getOriginalFilename())){
            uploadError = "File with this name already exists.";
            model.addAttribute("uploadError", uploadError);
        } else if(FileForm.getFileUpload().getSize() == 0){
            uploadError = "Please select file to upload.";
            model.addAttribute("uploadError", uploadError);
        } else{
            model.addAttribute("successNote", successNote);
            fileService.addFile(FileForm, authentication);
        }
        model.addAttribute("notes", noteService.getAllNotes(authentication));
        model.addAttribute("credentials", credentialService.getCredentialsByUser(u));
        model.addAttribute("files", fileService.getAllFiles(authentication));

        model.addAttribute("encryptionService",encryptionService);
        return "result";
    }

    @GetMapping("/delete-file-result")
    public String deleteFile(@RequestParam(value = "id") Integer id, @RequestParam(value = "fileid") Integer fileId,@ModelAttribute("NoteForm") NoteForm NoteForm,
                             @ModelAttribute("FileForm") FileForm FileForm,@ModelAttribute("CredentialForm") CredentialForm CredentialForm,
                             Model model, Authentication authentication){
        //Get data
        User u = userService.getUserByUsername(authentication.getName());

        File file = fileService.getFileById(fileId);
        fileService.deleteFile(u.getUserid(), file.getFileId());

        model.addAttribute("notes", noteService.getAllNotes(authentication));
        model.addAttribute("credentials", credentialService.getCredentialsByUser(u));
        model.addAttribute("files", fileService.getAllFiles(authentication));

        model.addAttribute("encryptionService",encryptionService);

        successNote = "File was successfully deleted.";
        model.addAttribute("successNote",successNote);
        return "result";
    }

    @GetMapping("/download-file")
    public ResponseEntity downloadFile(@RequestParam(value = "fileId") Integer fileId){
        File file = fileService.downloadFile(fileId);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(file.getContenttype()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                .body(new ByteArrayResource(file.getFiledata()));
    }
}
