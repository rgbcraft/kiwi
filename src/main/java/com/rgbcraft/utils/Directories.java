package com.rgbcraft.utils;

import com.rgbcraft.Kiwi;

import java.io.File;

public class Directories {
    public static File KIWI_DIRECTORY;
    public static File MCP_DIRECTORY;
    public static File BIN_DIRECTORY;
    public static File LIB_DIRECTORY;
    public static File NATIVES_DIRECTORY;
    public static File RUN_DIRECTORY;

    static {
        Directories.KIWI_DIRECTORY = new File(Kiwi.PROJECT.getProjectDir(), "kiwi");
        Directories.MCP_DIRECTORY = new File(Directories.KIWI_DIRECTORY, "mcp");
        Directories.BIN_DIRECTORY = new File(Directories.KIWI_DIRECTORY, "bin");
        Directories.LIB_DIRECTORY = new File(Directories.KIWI_DIRECTORY, "lib");
        Directories.NATIVES_DIRECTORY = new File(Directories.LIB_DIRECTORY, "natives");
        Directories.RUN_DIRECTORY = new File(Directories.KIWI_DIRECTORY, "run");
    }
}
