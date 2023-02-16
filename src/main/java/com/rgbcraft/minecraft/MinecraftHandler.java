package com.rgbcraft.minecraft;

import com.rgbcraft.Kiwi;
import com.rgbcraft.utils.Directories;
import com.rgbcraft.utils.FileUtils;
import com.rgbcraft.utils.PlatformUtils;
import org.gradle.api.logging.LogLevel;

import java.io.*;
import java.net.JarURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class MinecraftHandler {
    private static final URL CLIENT_URL;
    private static final URL SERVER_URL;

    private static final Map<URL, String> LIBRARIES;
    private static final URL[] NATIVES;

    public static void downloadMinecraft() throws IOException {
        Kiwi.LOGGER.log(LogLevel.INFO, "Downloading client.jar");
        FileUtils.downloadFile(CLIENT_URL, new File(Directories.BIN_DIRECTORY, "client.jar"));
        Kiwi.LOGGER.log(LogLevel.INFO, "Downloading server.jar");
        FileUtils.downloadFile(SERVER_URL, new File(Directories.BIN_DIRECTORY, "server.jar"));

        for (Map.Entry<URL, String> entry : LIBRARIES.entrySet()) {
            Kiwi.LOGGER.info("Downloading " + entry.getValue());
            FileUtils.downloadFile(entry.getKey(), new File(Directories.LIB_DIRECTORY, entry.getValue()));
        }

        for (URL n : NATIVES) {
            Kiwi.LOGGER.info("Downloading " + n.getPath().substring(n.getPath().lastIndexOf('/') + 1));
            JarURLConnection conn = (JarURLConnection) n.openConnection();
            JarFile file = conn.getJarFile();
            Enumeration<JarEntry> content = file.entries();
            while (content.hasMoreElements()) {
                JarEntry entry = content.nextElement();
                if (entry.getName().contains("META-INF")) continue;
                InputStream in = new BufferedInputStream(file.getInputStream(entry));
                ReadableByteChannel rbc = Channels.newChannel(in);
                FileOutputStream fos = new FileOutputStream(new File(Directories.NATIVES_DIRECTORY, entry.getName()));
                fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
                in.close();
                fos.close();
            }
        }
    }

    static {
        try {
            CLIENT_URL = new URL("https://launcher.mojang.com/v1/objects/53ed4b9d5c358ecfff2d8b846b4427b888287028/client.jar");
            SERVER_URL = new URL("https://launcher.mojang.com/v1/objects/2f0ec8efddd2f2c674c77be9ddb370b727dec676/server.jar");

            LIBRARIES = new HashMap<URL, String>() {{
                    put(new URL("https://libraries.minecraft.net/net/sf/jopt-simple/jopt-simple/4.5/jopt-simple-4.5.jar"), "jopt-simple-4.5.jar");
                    put(new URL("https://libraries.minecraft.net/org/ow2/asm/asm-all/4.1/asm-all-4.1.jar"), "asm-all-4.1.jar");
                    put(new URL("https://libraries.minecraft.net/net/java/jinput/jinput/2.0.5/jinput-2.0.5.jar"), "jinput-2.0.5.jar");
                    put(new URL("https://libraries.minecraft.net/net/java/jutils/jutils/1.0.0/jutils-1.0.0.jar"), "jutils-1.0.0.jar");
                    put(new URL("https://libraries.minecraft.net/org/lwjgl/lwjgl/lwjgl/2.9.0/lwjgl-2.9.0.jar"), "lwjgl-2.9.0.jar");
                    put(new URL("https://libraries.minecraft.net/org/lwjgl/lwjgl/lwjgl_util/2.9.0/lwjgl_util-2.9.0.jar"), "lwjgl_utils-2.9.0.jar");
            }};

            NATIVES = new URL[] {
                    new URL(PlatformUtils.getPlatform() == PlatformUtils.Platform.LINUX ? "jar:https://libraries.minecraft.net/org/lwjgl/lwjgl/lwjgl-platform/2.9.0/lwjgl-platform-2.9.0-natives-linux.jar!/" : PlatformUtils.getPlatform() == PlatformUtils.Platform.MACOS ? "jar:https://libraries.minecraft.net/org/lwjgl/lwjgl/lwjgl-platform/2.9.0/lwjgl-platform-2.9.0-natives-osx.jar!/" : "jar:https://libraries.minecraft.net/org/lwjgl/lwjgl/lwjgl-platform/2.9.0/lwjgl-platform-2.9.0-natives-windows.jar!/"),
                    new URL(PlatformUtils.getPlatform() == PlatformUtils.Platform.LINUX ? "jar:https://libraries.minecraft.net/net/java/jinput/jinput-platform/2.0.5/jinput-platform-2.0.5-natives-linux.jar!/" : PlatformUtils.getPlatform() == PlatformUtils.Platform.MACOS ? "jar:https://libraries.minecraft.net/net/java/jinput/jinput-platform/2.0.5/jinput-platform-2.0.5-natives-osx.jar!/" : "jar:https://libraries.minecraft.net/net/java/jinput/jinput-platform/2.0.5/jinput-platform-2.0.5-natives-windows.jar!/")
            };
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }
}
