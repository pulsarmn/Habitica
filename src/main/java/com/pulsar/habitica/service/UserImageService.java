package com.pulsar.habitica.service;

import com.pulsar.habitica.dao.user.UserImageDao;
import com.pulsar.habitica.entity.user.User;
import com.pulsar.habitica.entity.user.UserImage;
import com.pulsar.habitica.util.PropertiesUtil;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

public class UserImageService {

    private final UserImageDao userImageDao;
    private static final String RELATIVE_PATH = PropertiesUtil.get("image.base.path");
    private static final String ABSOLUTE_PATH = PropertiesUtil.get("image.absolute.path");
    private static final String USER_AVATAR = PropertiesUtil.get("image.avatar.name");
    private static final String EMPTY_AVATAR = PropertiesUtil.get("image.empty.avatar");

    public UserImageService(UserImageDao userImageDao) {
        this.userImageDao = userImageDao;
    }

    public UserImage initUserImage(int userId) throws IOException {
        var userImage = UserImage.builder()
                .userId(userId)
                .imageAddr(RELATIVE_PATH + EMPTY_AVATAR)
                .build();
        return userImageDao.save(userImage);
    }

    public UserImage uploadUserImage(int userId, InputStream imageContent) throws IOException {
        Path fullImagePath = Path.of(ABSOLUTE_PATH, "user" + userId, USER_AVATAR);
        Path relativePath = Path.of(RELATIVE_PATH, "user" + userId, USER_AVATAR);
        System.out.println(fullImagePath);
        try (imageContent) {
            Files.createDirectories(fullImagePath.getParent());
            Files.write(fullImagePath, imageContent.readAllBytes());
        }
        return userImageDao.update(UserImage.builder()
                .userId(userId)
                .imageAddr(relativePath.toString())
                .build());
    }

    public UserImage findUserImage(int userId) {
        return userImageDao.findById(userId).get();
    }
}
