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
    File getFile(Integer fileId);
    @Select("SELECT * FROM FILES WHERE userid=#{userid}")
    @Results(
        @Result(column = "filedata", typeHandler = ByteArrayTypeHandler.class)
    )
    List<File> getFilesByUser(Integer userid);

    @Insert("INSERT INTO FILES (filename, contenttype, filesize, userid, filedata) VALUES (#{filename}, #{contenttype}, #{filesize}, #{userid}, #{filedata})")
    @Options(useGeneratedKeys = true, keyProperty = "fileId")
    //String filename, String contenttype, String filesize, Integer userid, Blob filedata
    int insertFile(File file);

    @Update("UPDATE FILES SET filename=#{filename}, contenttype=#{contenttype}, filesize=#{filesize}, userid=#{userid}, filedata=#{filedata} WHERE fileId=#{fileId}")
    void updateCred(@NotNull Integer fileId, String filename, String contenttype, String filesize, Integer userid, Blob filedata);

    @Delete("DELETE FROM FILES WHERE fileId={fileId}")
    void deleteFile(Integer fileId);
}