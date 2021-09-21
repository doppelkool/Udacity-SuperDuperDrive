package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import org.apache.ibatis.annotations.*;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotNull;
import java.util.List;

@Mapper
public interface CredentialMapper {
    @Select("SELECT * FROM CREDENTIALS WHERE credentialid=#{credentialid}")
    Credential getCredByid(Integer credentialid);
    @Select("SELECT * FROM CREDENTIALS WHERE userid=#{userid}")
    List<Credential> getCredentialByUserid(Integer userid);
    @Select("SELECT * FROM CREDENTIALS")
    List<Credential> getCredentials();
    @Select("SELECT Max(credentialid) FROM CREDENTIALS")
    int getMaxID();

    @Insert("INSERT INTO CREDENTIALS (url, username, key, password, userid) VALUES (#{url}, #{username}, #{key}, #{password}, #{userid})")
    @Options(keyProperty = "credentialid", useGeneratedKeys = true)
    int insertCred(Credential credential);

    @Update("UPDATE CREDENTIALS SET url = #{newUrl}, username = #{newUsername}, password = #{newPassword} WHERE credentialid = #{credentialid}")
    int updateCredential(String newUrl, String newUsername, String newPassword, Integer credentialid);

    @Delete("DELETE FROM CREDENTIALS WHERE credentialid=#{credentialid}")
    void deleteCred(Integer credentialid);
}