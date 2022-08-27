package com.udacity.jwdnd.course1.cloudstorage.model;

public class File {


    private Integer fileId;
    private User user;
    private String fileName;
    private String contentType;
    private Integer fileSize;
    private byte[] fileData;

    public File() {
    }

    public File(Integer fileId, User user, String fileName, String contentType, Integer fileSize, byte[] fileData) {
        setFileId(fileId);
        setUser(user);
        setFileName(fileName);
        setContentType(contentType);
        setFileData(fileData);
        setFileSize(fileSize);
    }
    public Integer getFileId() {
        return fileId;
    }

    public void setFileId(Integer fileId) {
        this.fileId = fileId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public Integer getFileSize() {
        return fileSize;
    }

    public void setFileSize(Integer fileSize) {
        this.fileSize = fileSize;
    }

    public byte[] getFileData() {
        return fileData;
    }

    public void setFileData(byte[] fileData) {
        this.fileData = fileData;
    }


}
