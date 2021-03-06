package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.apache.ibatis.annotations.*;

import javax.validation.constraints.NotNull;

@Mapper
public interface UserMapper {
    @Select("SELECT * FROM USERS WHERE userid = #{userid}")
    User getUser(Integer userid);
    @Select("SELECT * FROM USERS WHERE username=#{username}")
    User getUserByUName(String username);

    @Insert("INSERT INTO USERS (username, salt, password, firstname, lastname) VALUES (#{username}, #{salt}, #{password}, #{firstname}, #{lastname})")
    @Options(useGeneratedKeys = true, keyProperty = "userid")
    int insertUser(User user);

    @Update("UPDATE USERS SET username=#{username}, salt=#{salt}, password=#{password}, firstname=#{firstname}, lastname=#{lastname} WHERE userid=#{userid}")
    void updateCred(@NotNull Integer userid, String username, String salt, String password, String firstname, String lastname);

    @Delete("DELETE FROM USERS WHERE username=#{username}")
    void deleteUser(String username);
}