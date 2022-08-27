package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.exception.DuplicateEntityException;
import com.udacity.jwdnd.course1.cloudstorage.exception.EntityNotFoundException;
import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

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

    public Integer insert(File file, Authentication authentication) throws DuplicateEntityException {
        User currentUser = userService.getCurrentUser(authentication);
        if (fileMapper.exists(file, currentUser)) {
            throw new DuplicateEntityException("File '" + file.getFileName() + "' already exists for this user");
        }
        return fileMapper.insert(new File(null, currentUser, file.getFileName(), file.getContentType(), file.getFileSize(), file.getFileData()));
    }

    public boolean fileExists(File file, Authentication authentication) {
        User currentUser = userService.getCurrentUser(authentication);
        return fileMapper.exists(file, currentUser);
    }

    public File getFile(Integer fileId, Authentication authentication) {
        User currentUser = userService.getCurrentUser(authentication);
        File file = fileMapper.getFile(fileId);
        if (file == null || !fileMapper.exists(file, currentUser)) {
            throw new EntityNotFoundException("Resource could not be found for this user");
        }
        return file;
    }

    public void deleteFile(int fileId) {
        fileMapper.delete(fileId);
    }
}
