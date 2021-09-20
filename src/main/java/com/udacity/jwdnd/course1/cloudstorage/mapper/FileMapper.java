package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.BlobTypeHandler;
import org.apache.ibatis.type.ByteArrayTypeHandler;
import org.apache.ibatis.type.JdbcType;

import javax.validation.constraints.NotNull;
import java.sql.Blob;
import java.util.List;

@Mapper
public interface FileMapper {
    @Select("SELECT * FROM FILES WHERE fileId=#{fileId}")
    File getFileByID(Integer fileId);
    @Select("SELECT * FROM FILES WHERE filename=#{filename}")
    File getFileByName(String filename);
    @Select("SELECT * FROM FILES WHERE userid=#{userid}")
    List<File> getFilesByUser(Integer userid);

    @Insert("INSERT INTO FILES (filename, contenttype, filesize, userid, filedata) VALUES (#{filename}, #{contenttype}, #{filesize}, #{userid}, #{filedata})")
    @Options(useGeneratedKeys = true, keyProperty = "fileId")
    int insertFile(File file);

    @Delete("DELETE FROM FILES WHERE fileId=#{fileId}")
    void deleteFile(Integer fileId);
}