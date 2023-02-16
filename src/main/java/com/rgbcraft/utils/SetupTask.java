package com.rgbcraft.utils;

import com.rgbcraft.Kiwi;
import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.TaskAction;

import java.io.IOException;

public class SetupTask extends DefaultTask {
    @TaskAction
    public void setup() throws IOException {
        CreateDirectories.createProjectDirectories(Kiwi.PROJECT);
    }
}
