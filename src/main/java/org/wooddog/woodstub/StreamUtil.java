/******************************************************************************
 * Woodstub                                                                   *
 * Copyright (c) 2011                                                         *
 * Developed by Claus Brøndby Reimer & Asbjørn Andersen                       *
 * All rights reserved                                                        *
 ******************************************************************************/

package org.wooddog.woodstub;

import java.io.*;

public class StreamUtil {
    public static byte[] read(InputStream in) throws IOException {
        ByteArrayOutputStream out = null;

        try {
            out = new ByteArrayOutputStream();
            copy(in, out);
        } finally {
            close(out);
            close(in);
        }

        return out.toByteArray();
    }

    private static void copy(InputStream in, OutputStream out) throws IOException {
        int length;

        byte[] data = new byte[4096];
        while ((length = in.read(data)) != -1) {
            out.write(data, 0, length);
        }
    }

    private static void close(Closeable resource) {
        if (resource != null) {
            try {
                resource.close();
            } catch (IOException x) {
                x.printStackTrace();
            }
        }
    }
}
