package com.pulsar.habitica.filter;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public final class PagePaths {

    private static final Set<String> PUBLIC_PATHS;
    private static final Set<String> PRIVATE_PATHS;
    private static final Set<String> GUEST_PATHS;

    static {
        PUBLIC_PATHS = Arrays.stream(PublicPaths.values())
                .map(PublicPaths::getPath)
                .collect(Collectors.toSet());
        PRIVATE_PATHS = Arrays.stream(PrivatePaths.values())
                .map(PrivatePaths::getPath)
                .collect(Collectors.toSet());
        GUEST_PATHS = Arrays.stream(GuestPaths.values())
                .map(GuestPaths::getPath)
                .collect(Collectors.toSet());
    }

    private PagePaths() {}

    public static Set<String> getPublicPaths() {
        return PUBLIC_PATHS;
    }

    public static Set<String> getPrivatePaths() {
        return PRIVATE_PATHS;
    }

    public static Set<String> getGuestPaths() {
        return GUEST_PATHS;
    }
}
