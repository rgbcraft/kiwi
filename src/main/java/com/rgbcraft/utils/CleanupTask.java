package com.rgbcraft.utils;

import org.apache.tools.ant.util.FileUtils;
import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.TaskAction;

import java.io.IOException;
import java.nio.file.Files;
import java.util.Comparator;

public class CleanupTask extends DefaultTask {
    @TaskAction
    public void cleanup() throws IOException {
        if (Directories.KIWI_DIRECTORY.exists()) {
            Files.walk(Directories.KIWI_DIRECTORY.toPath()).sorted(Comparator.reverseOrder()).forEach(path -> {
                try {
                    Files.delete(path);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        }
    }
}
