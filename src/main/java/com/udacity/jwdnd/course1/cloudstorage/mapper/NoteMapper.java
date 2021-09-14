package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.apache.ibatis.annotations.*;

import javax.validation.constraints.NotNull;
import java.util.List;

@Mapper
public interface NoteMapper {
    @Select("SELECT * FROM NOTES WHERE noteid=#{noteid}")
    Note getNoteByID(Integer noteid);
    @Select("SELECT * FROM NOTES WHERE notetitle=#{notetitle}")
    Note getNoteByTitle(String notetitle);
    @Select("SELECT * FROM NOTES WHERE userid=#{userid}")
    @Results({
            @Result(property = "noteid", column = "noteid"),
            @Result(property = "notetitle", column = "notetitle"),
            @Result(property = "notedescription", column = "notedescription"),
            @Result(property = "userid", column = "userid")
    })
    List<Note> getNotesByUserId(Integer userid);

    @Insert("INSERT INTO NOTES (notetitle, notedescription, userid) VALUES (#{notetitle}, #{notedescription}, #{userid})")
    @Options(useGeneratedKeys = true, keyProperty = "noteid")
    int insertNote(Note note);

    @Update("UPDATE NOTES SET notetitle=#{notetitle}, notedescription=#{notedescription}, userid=#{userid} WHERE noteid=#{noteid}")
    void updateCred(@NotNull Integer noteid, String notetitle, String notedescription, Integer userid);

    @Delete("DELETE FROM NOTES WHERE noteid=#{noteid}")
    void deleteNote(Integer noteid);
}