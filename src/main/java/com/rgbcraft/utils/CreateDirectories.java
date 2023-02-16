package com.rgbcraft.utils;

import com.rgbcraft.Kiwi;
import com.rgbcraft.minecraft.MinecraftHandler;
import org.gradle.api.Project;

import java.io.File;
import java.io.IOException;

public class CreateDirectories {
    public static void createProjectDirectories(Project project) throws IOException {
        Kiwi.LOGGER.info("Creating project directories");
        if (!new File(project.getProjectDir(), "kiwi").exists()) {
            Directories.KIWI_DIRECTORY = project.mkdir("kiwi");
            if (!Directories.MCP_DIRECTORY.mkdir()) {
                throw new IOException("Failed to create MCP directory!");
            }
            if (!Directories.BIN_DIRECTORY.mkdir()) {
                throw new IOException("Failed to create BIN directory!");
            }

            if (!Directories.LIB_DIRECTORY.mkdir()) {
                throw new IOException("Failed to create LIB directory!");
            }

            if (!Directories.NATIVES_DIRECTORY.mkdir()) {
                throw new IOException("Failed to create NATIVES directory!");
            }

            if (!Directories.RUN_DIRECTORY.mkdir()) {
                throw new IOException("Failed to create RUN directory!");
            }
        }
    }
}
