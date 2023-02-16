package com.rgbcraft.utils;

public class PlatformUtils {
    public enum Platform {
        LINUX,
        MACOS,
        WINDOWS,

        UNKNOWN,
    }

    public static Platform getPlatform() {
        String os = System.getProperty("os.name").toLowerCase();

        if (os.contains("windows")) {
            return Platform.WINDOWS;
        } else if (os.contains("mac")) {
            return Platform.MACOS;
        } else if (os.contains("linux")) {
            return Platform.LINUX;
        }

        return Platform.UNKNOWN;
    }
}
