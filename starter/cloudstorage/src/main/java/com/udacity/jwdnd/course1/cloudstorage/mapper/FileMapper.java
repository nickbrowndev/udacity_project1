package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface FileMapper {
    @Select("SELECT * FROM FILES WHERE userId = #{userId}")
    List<File> getFilesForUser(User user);

    @Select("SELECT f.*, u.userid as userid FROM FILES f JOIN USERS u ON f.userid = u.userid WHERE fileId = #{fileId}")
    File getFile(Integer fileId);

    @Insert("INSERT INTO FILES (fileName, contentType, fileSize, fileData, userId) VALUES(#{fileName}, #{contentType}, #{fileSize}, #{fileData}, #{user.userId})")
    @Options(useGeneratedKeys = true, keyProperty = "fileId")
    int insert(File file);

    @Delete("DELETE FROM FILES WHERE fileid = ${fileId}")
    void delete(Integer fileId);

    @Select("SELECT COUNT(1) FROM FILES WHERE filename = #{file.fileName} and userId = #{user.userId}")
    boolean exists(File file, User user);
}
