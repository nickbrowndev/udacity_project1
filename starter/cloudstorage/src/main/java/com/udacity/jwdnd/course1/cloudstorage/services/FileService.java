package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

@Service
public class FileService {

    private final FileMapper fileMapper;
    private final UserService userService;

    public FileService(FileMapper fileMapper, UserService userService) {
        this.fileMapper = Objects.requireNonNull(fileMapper);
        this.userService = Objects.requireNonNull(userService);
    }

    public List<File> getFilesForUser(Authentication authentication) {
        User currentUser = userService.getCurrentUser(authentication);
        return fileMapper.getFilesForUser(currentUser);
    }

    public Integer insert(File file, Authentication authentication) throws IOException {
        User currentUser = userService.getCurrentUser(authentication);
        return fileMapper.insert(new File(null, currentUser, file.getFileName(), file.getContentType(), file.getFileSize(), file.getFileData()));
    }
}
