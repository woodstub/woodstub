/******************************************************************************
 * Woodstub                                                                   *
 * Copyright (c) 2011                                                         *
 * Developed by Claus Brøndby Reimer & Asbjørn Andersen                       *
 * All rights reserved                                                        *
 ******************************************************************************/

package org.wooddog.woodstub.compiler;

import org.wooddog.woodstub.StreamUtil;
import org.wooddog.woodstub.StubException;

import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardLocation;
import javax.tools.ToolProvider;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Compiler {
    private final MemoryJavaFileManager manager;
    private final JavaCompiler compiler;
    private static final Map<String, JavaFileObject> sourceFiles = new HashMap<String, JavaFileObject>();

    public Compiler() {
        compiler = ToolProvider.getSystemJavaCompiler();
        manager = new MemoryJavaFileManager(compiler.getStandardFileManager(null, null, null));
    }

    public void compile() {
        JavaCompiler.CompilationTask task= compiler.getTask(null, manager, null, null, null, sourceFiles.values());

        if (!task.call()) {
            // TODO: more descriptive
            throw new StubException("unable to compile stub classes");
        }
    }

    public void addSourceFile(String className, String source) throws IOException {
        if (sourceFiles.containsKey(className)) {
            System.out.println("Skipped one!");
            return;
        }

        JavaFileObject object = new MemoryJavaFileObject(className, JavaFileObject.Kind.SOURCE);
        object.openOutputStream().write(source.getBytes());

        sourceFiles.put(className, object);
    }

    public byte[] getByteCode(String className) throws IOException {
        JavaFileObject object = manager.getJavaFileForInput(StandardLocation.CLASS_OUTPUT, className, JavaFileObject.Kind.CLASS);
        return StreamUtil.read(object.openInputStream());
    }

    public void cleanup() {
        manager.cleanup();
        sourceFiles.clear();
    }

    public boolean hasSource(String className) {
        return sourceFiles.containsKey(className);
    }

    public static void clearSources() {
        sourceFiles.clear();
    }

}
