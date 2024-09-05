package com.pulsar.habitica.service;

import com.pulsar.habitica.dao.user.UserImageDao;
import com.pulsar.habitica.entity.user.UserImage;
import com.pulsar.habitica.util.PropertiesUtil;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

public class UserImageService {

    private final UserImageDao userImageDao;
    private static final String BASE_PATH = PropertiesUtil.get("image.base.path");
    private static final String EMPTY_AVATAR = PropertiesUtil.get("image.empty.avatar");
    private static final String ROOT_PATH = PropertiesUtil.get("image.root.path");
    private static final String DEPLOY_PATH = PropertiesUtil.get("image.deploy.path");

    public UserImageService(UserImageDao userImageDao) {
        this.userImageDao = userImageDao;
    }

    public UserImage initUserImage(int userId) throws IOException {
        var userImage = UserImage.builder()
                .userId(userId)
                .imageAddr(BASE_PATH + EMPTY_AVATAR)
                .build();
        return userImageDao.save(userImage);
    }

    public UserImage uploadUserImage(int userId, InputStream imageContent) throws IOException {
        var userImage = findOrCreateUserImage(userId);
        if (userImage.getImageAddr().equals(EMPTY_AVATAR)) {
            saveUserImage(userImage);
        }
        String imageName = generateImageName();
        String userFolder = generateUserFolder(userId);

        Path fullImagePath = Path.of(DEPLOY_PATH, BASE_PATH, userFolder, imageName);
        Path relativePath = Path.of(BASE_PATH, userFolder, imageName);
        Path projectPath = Path.of(ROOT_PATH, BASE_PATH, userFolder, imageName);

        deleteDirectories(fullImagePath, projectPath);

        var imageBytes = readImageContent(imageContent);

        saveImageToDisk(fullImagePath, imageBytes);
        saveImageToDisk(projectPath, imageBytes);

        return userImageDao.update(UserImage.builder()
                .userId(userId)
                .imageAddr(relativePath.toString())
                .build());
    }

    public UserImage findOrCreateUserImage(int userId) {
        var userImage = userImageDao.findById(userId);
        return userImage.orElseGet(() -> UserImage.builder()
                .userId(userId)
                .imageAddr(EMPTY_AVATAR)
                .build());
    }

    public UserImage saveUserImage(UserImage userImage) {
        return userImageDao.save(userImage);
    }

    private String generateImageName() {
        return String.valueOf(System.currentTimeMillis());
    }

    private String generateUserFolder(int userId) {
        return "user" + userId;
    }

    private void deleteDirectories(Path...directories) {
        for (var path : directories) {
            deleteDirectory(path.getParent().toFile());
        }
    }

    private void deleteDirectory(File directory) {
        File[] contents = directory.listFiles();
        if (contents != null) {
            for (var item : contents) {
                deleteDirectory(item);
            }
        }
        directory.delete();
    }

    private byte[] readImageContent(InputStream inputStream) throws IOException {
        try (var baos = new ByteArrayOutputStream()) {
            byte[] buffer = new byte[1024];
            int len;
            while ((len = inputStream.read(buffer)) != -1) {
                baos.write(buffer, 0, len);
            }
            return baos.toByteArray();
        }
    }

    private void saveImageToDisk(Path imagePath, byte[] bytes) throws IOException {
        Files.createDirectories(imagePath.getParent());
        Files.write(imagePath, bytes);
    }
}