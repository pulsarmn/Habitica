package com.pulsar.habitica.service;

import com.pulsar.habitica.dao.user.UserImageDao;
import com.pulsar.habitica.entity.user.UserImage;
import com.pulsar.habitica.util.PropertiesUtil;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

public class UserImageService {

    private final UserImageDao userImageDao;
    private static final String BASE_PATH = PropertiesUtil.get("image.base.path");
    private static final String EMPTY_AVATAR = PropertiesUtil.get("image.empty.avatar");

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

    public UserImage uploadUserImage(int userId, String rootPath, InputStream imageContent) throws IOException {
        var userImage = findUserImage(userId);
        if (userImage.getImageAddr().equals(EMPTY_AVATAR)) {
            saveUserImage(userImage);
        }
        String imageName = System.currentTimeMillis() + "";
        String userFolder = "user" + userId;
        Path fullImagePath = Path.of(rootPath, BASE_PATH, userFolder, imageName);
        Path relativePath = Path.of(BASE_PATH, userFolder, imageName);

        writeUserImage(fullImagePath, imageContent);

        return userImageDao.update(UserImage.builder()
                .userId(userId)
                .imageAddr(relativePath.toString())
                .build());
    }

    public UserImage findUserImage(int userId) {
        var userImage = userImageDao.findById(userId);
        return userImage.orElseGet(() -> UserImage.builder()
                .userId(userId)
                .imageAddr(EMPTY_AVATAR)
                .build());
    }

    public UserImage saveUserImage(UserImage userImage) {
        return userImageDao.save(userImage);
    }

    private void writeUserImage(Path imagePath, InputStream imageContent) throws IOException {
        try (imageContent) {
            Files.createDirectories(imagePath.getParent());
            Files.write(imagePath, imageContent.readAllBytes());
        }
    }
}