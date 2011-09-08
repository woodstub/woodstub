/******************************************************************************
 * Woodstub                                                                   *
 * Copyright (c) 2011                                                         *
 * Developed by Claus Brøndby Reimer & Asbjørn Andersen                       *
 * All rights reserved                                                        *
 ******************************************************************************/

package org.wooddog.woodstub.compiler;

import javax.tools.SimpleJavaFileObject;
import java.io.*;
import java.net.URI;

class MemoryJavaFileObject extends SimpleJavaFileObject {
    private final ByteArrayOutputStream content;

    protected MemoryJavaFileObject(String className, Kind kind) {
        super(URI.create("string:///" + className.replace(".", "/") + kind.extension), kind);
        content = new ByteArrayOutputStream();
    }

    @Override
    public OutputStream openOutputStream() throws IOException {
        return content;
    }

    @Override
    public InputStream openInputStream() throws IOException {
        return new ByteArrayInputStream(content.toByteArray());
    }

    @Override
    public CharSequence getCharContent(boolean ignoreEncodingErrors) {
        return new String(content.toByteArray());
    }
}
