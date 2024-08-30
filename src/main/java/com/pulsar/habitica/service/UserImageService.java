package com.pulsar.habitica.service;

import com.pulsar.habitica.dao.user.UserImageDao;
import com.pulsar.habitica.entity.user.User;
import com.pulsar.habitica.entity.user.UserImage;
import com.pulsar.habitica.util.PropertiesUtil;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

public class UserImageService {

    private final UserImageDao userImageDao;
    private static final String RELATIVE_PATH = PropertiesUtil.get("image.base.path");
    private static final String ABSOLUTE_PATH = PropertiesUtil.get("image.absolute.path");
    private static final String USER_AVATAR = PropertiesUtil.get("image.avatar.name");
    private static final String EMPTY_AVATAR = PropertiesUtil.get("image.empty.avatar");

    public UserImageService(UserImageDao userImageDao) {
        this.userImageDao = userImageDao;
    }
}
