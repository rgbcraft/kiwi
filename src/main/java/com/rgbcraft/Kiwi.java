package com.rgbcraft;

import com.rgbcraft.minecraft.LibrariesTask;
import com.rgbcraft.utils.CleanupTask;
import com.rgbcraft.utils.Directories;
import com.rgbcraft.utils.SetupTask;
import org.cadixdev.lorenz.io.MappingFormats;
import org.cadixdev.lorenz.io.MappingsReader;
import org.cadixdev.mercury.Mercury;
import org.cadixdev.mercury.remapper.MercuryRemapper;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.logging.Logger;

import java.io.File;
import java.util.Objects;

public class Kiwi implements Plugin<Project> {
    public static Logger LOGGER;
    public static Project PROJECT;
    @Override
    public void apply(Project project) {
        PROJECT = project;
        LOGGER = project.getLogger();

        try {
            MappingsReader reader = MappingFormats.SRG.createReader(getClass().getResourceAsStream("/test.srg"));
            Mercury mercury = new Mercury();
            mercury.getClassPath().add(Directories.BIN_DIRECTORY.toPath().resolve("client.jar"));
            for (File file : Objects.requireNonNull(Directories.LIB_DIRECTORY.listFiles())) {
                mercury.getClassPath().add(file.toPath());
            }
            System.out.println(reader.read().getClassMapping("q.f"));
            mercury.getProcessors().add(MercuryRemapper.create(reader.read()));
//            reader = MappingFormats.SRG.createReader(getClass().getResourceAsStream("/test.srg"));
//            mercury.getProcessors().add(MercuryRemapper.create(reader.read()));
            mercury.rewrite(Directories.BIN_DIRECTORY.toPath().resolve("client"), Directories.BIN_DIRECTORY.toPath().resolve("client_mapped"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        project.getTasks().register("setup", SetupTask.class);
        project.getTasks().register("libraries", LibrariesTask.class);
        project.getTasks().register("cleanup", CleanupTask.class);
    }
}
