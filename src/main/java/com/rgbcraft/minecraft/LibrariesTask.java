package com.rgbcraft.minecraft;

import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.TaskAction;

import java.io.IOException;

public class LibrariesTask extends DefaultTask {

    public LibrariesTask() {
        dependsOn(":setup");
    }

    @TaskAction
    public void download() throws IOException {
        MinecraftHandler.downloadMinecraft();
    }
}
