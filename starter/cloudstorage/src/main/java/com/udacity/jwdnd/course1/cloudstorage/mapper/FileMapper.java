package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.apache.ibatis.annotations.*;
import org.springframework.core.io.Resource;

import java.util.List;

@Mapper
public interface FileMapper {
    @Select("SELECT * FROM FILES WHERE userId = #{userId}")
    List<File> getFilesForUser(User user);

    @Insert("INSERT INTO FILES (fileName, contentType, fileSize, fileData, userid) VALUES(#{fileName}, #{contentType}, #{fileSize}, #{fileData}, #{user.userId})")
    @Options(useGeneratedKeys = true, keyProperty = "fileId")
    int insert(File file);

    @Delete("DELETE FROM FILES WHERE fileid = ${fileId}")
    void delete(Integer fileId);
}
