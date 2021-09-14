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

    //String url, String username, String key, String password, Integer userid
    @Insert("INSERT INTO CREDENTIALS (url, username, key, password, userid) VALUES (#{url}, #{username}, #{key}, #{password}, #{userid})")
    @Options(keyProperty = "credentialid", useGeneratedKeys = true)
    int insertCred(Credential credential);

    @Update("UPDATE CREDENTIALS SET url=#{url}, username=#{username}, key=#{key}, password=#{password}, userid=#{userid} WHERE credentialid=#{credentialid}")
    void updateCred(@NotNull Integer credentialid, String url, String username, String key, String password, Integer userid);

    @Delete("DELETE FROM CREDENTIALS WHERE credentialid=#{credentialid}")
    void deleteCred(Integer credentialid);
}