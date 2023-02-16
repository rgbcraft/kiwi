package com.rgbcraft.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

public class FileUtils {
    public static void downloadFile(URL url, File output) throws IOException {
        InputStream in = url.openStream();
        ReadableByteChannel rbc = Channels.newChannel(in);
        FileOutputStream fos = new FileOutputStream(output);
        fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
    }
}
