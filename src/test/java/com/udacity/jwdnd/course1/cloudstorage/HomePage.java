package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;

import java.util.List;

public class HomePage {

    @FindBy(id = "logout-button")
    private WebElement logoutButton;

    @FindBy(id = "fileUpload")
    private WebElement fileUpload;

    @FindBy(id = "uploadButton")
    private WebElement uploadButton;


    @FindBy(id = "file-name")
    private WebElement modal_ShowFile_name;

    @FindBy(id = "file-size")
    private WebElement modal_ShowFile_size;

    @FindBy(id = "submit_fileDownload")
    private WebElement modal_ShowFile_downloadButton;



    @FindBy(id = "note-title")
    private WebElement note_title_input;

    @FindBy(id = "note-description")
    private WebElement note_desc_input;

    @FindBy(id = "save-note-button")
    private WebElement newNote_Submit;



    @FindBy(id = "credential-url")
    private WebElement cred_url_input;

    @FindBy(id = "credential-username")
    private WebElement cred_username_input;

    @FindBy(id = "credential-password")
    private WebElement cred_password_input;

    @FindBy(id = "credential-submit")
    private WebElement cred_input_submit;


    @FindBy(id = "credentialEdit-url")
    private WebElement edit_cred_url_input;

    @FindBy(id = "credentialEdit-username")
    private WebElement edit_cred_username_input;

    @FindBy(id = "credentialEdit-password")
    private WebElement edit_cred_password_input;

    @FindBy(id = "save-edit-credential")
    private WebElement edit_cred_input_submit;


    public void createNote(String newNoteTitle, String newNoteDesc)
    {
        getNotes_tab().click();
        getNewNoteButton().click();

        getNote_title_input().sendKeys(newNoteTitle);
        getNote_desc_input().sendKeys(newNoteDesc);

        getNewNote_Submit().click();
    }

    public void editNote(String newTitle, String newDesc)
    {
        getNotes_tab().click();
        getNote_edit_button().click();

        getEditNote_title().sendKeys(newTitle);
        getEditNote_description().sendKeys(newDesc);

        getEdit_note_button().click();
    }

    @FindBy(id = "note-title-display")
    private List<WebElement> notetitledisplaylist;

    public List<WebElement> getnotetitledisplaylist() {
        return notetitledisplaylist;
    }


    @FindBy(id = "note-title-display")
    private WebElement noteTitleDisplay;

    public WebElement getNoteTitleDisplay() {
        return noteTitleDisplay;
    }

    @FindBy(id = "note-description-display")
    private WebElement noteDescrDisplay;


    public WebElement getNoteDescriptionDisplay(){
        return noteDescrDisplay;
    }


    @FindBy(id = "edit-note-button")
    private WebElement edit_note_button;

    public WebElement getEdit_note_button() {
        return edit_note_button;
    }

    @FindBy(id = "editNote-title")
    private WebElement editNote_title;

    public WebElement getEditNote_title() {
        return editNote_title;
    }

    @FindBy(id = "editNote-description")
    private WebElement editNote_description;

    public WebElement getEditNote_description() {
        return editNote_description;
    }

    @FindBy(id = "note-edit-button")
    private WebElement note_edit_button;

    public WebElement getNote_edit_button() {
        return note_edit_button;
    }

    @FindBy(id = "nav-files-tab")
    private WebElement files_tab;

    public WebElement getFiles_tab() {
        return files_tab;
    }

    @FindBy(id = "nav-notes-tab")
    private WebElement notes_tab;

    public WebElement getNotes_tab() {
        return notes_tab;
    }

    @FindBy(id = "open-note-modal")
    private WebElement newNoteButton;

    public WebElement getNewNoteButton() {
        return newNoteButton;
    }

    @FindBy(id = "nav-credentials-tab")
    private WebElement creds_tab;

    public WebElement getCreds_tab() {
        return creds_tab;
    }

    @FindBy(id = "open-credentials-modal")
    private WebElement newCredButton;

    public WebElement getNewCredButton() {
        return newCredButton;
    }


    public WebElement getLogoutButton() {
        return logoutButton;
    }

    public WebElement getFileUpload() {
        return fileUpload;
    }

    public WebElement getUploadButton() {
        return uploadButton;
    }

    public WebElement getModal_ShowFile_name() {
        return modal_ShowFile_name;
    }

    public WebElement getModal_ShowFile_size() {
        return modal_ShowFile_size;
    }

    public WebElement getModal_ShowFile_downloadButton() {
        return modal_ShowFile_downloadButton;
    }

    public WebElement getNote_title_input() {
        return note_title_input;
    }

    public WebElement getNote_desc_input() {
        return note_desc_input;
    }

    public WebElement getNewNote_Submit() {
        return newNote_Submit;
    }

    public WebElement getCred_url_input() {
        return cred_url_input;
    }

    public WebElement getCred_username_input() {
        return cred_username_input;
    }

    public WebElement getCred_password_input() {
        return cred_password_input;
    }

    public WebElement getCred_input_submit() {
        return cred_input_submit;
    }

    public WebElement getEdit_cred_url_input() {
        return edit_cred_url_input;
    }

    public WebElement getEdit_cred_username_input() {
        return edit_cred_username_input;
    }

    public WebElement getEdit_cred_password_input() {
        return edit_cred_password_input;
    }

    public WebElement getEdit_cred_input_submit() {
        return edit_cred_input_submit;
    }
}
