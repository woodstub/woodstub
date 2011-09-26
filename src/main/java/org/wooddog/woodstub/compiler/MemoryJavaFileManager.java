/******************************************************************************
 * Woodstub                                                                   *
 * Copyright (c) 2011                                                         *
 * Developed by Claus Brøndby Reimer & Asbjørn Andersen                       *
 * All rights reserved                                                        *
 ******************************************************************************/

package org.wooddog.woodstub.compiler;

import javax.tools.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

class MemoryJavaFileManager extends ForwardingJavaFileManager {
    private final Map<String, Map<String, MemoryJavaFileObject>> locations;


    public MemoryJavaFileManager(StandardJavaFileManager manager) {
        super(manager);
        locations = new HashMap<String, Map<String, MemoryJavaFileObject>>();
    }


    @Override
    public JavaFileObject getJavaFileForOutput(Location location, String className, JavaFileObject.Kind kind,
                                               FileObject sibling) throws IOException {
        Map<String, MemoryJavaFileObject> files = getFilesFromLocation(location);

        MemoryJavaFileObject object = files.get(className + kind.extension);
        if (object == null) {
            object = new MemoryJavaFileObject(className, kind);
            files.put(className + kind.extension, object);
        }

        return object;
    }

    private Map<String, MemoryJavaFileObject> getFilesFromLocation(Location location) {
        Map<String, MemoryJavaFileObject> files = locations.get(location.getName());
        if (files == null) {
            files = new HashMap<String, MemoryJavaFileObject>();
            locations.put(location.getName(), files);
        }
        return files;
    }


    @Override
    public JavaFileObject getJavaFileForInput(Location location, String className, JavaFileObject.Kind kind) throws IOException {
        Map<String, MemoryJavaFileObject> files = locations.get(location.getName());
        if (files == null) {
            throw new FileNotFoundException("location " + location.getName() + " not found");
        }

        MemoryJavaFileObject object = files.get(className + kind.extension);
        if (object == null) {
            throw new FileNotFoundException("file " + location.getName() + ":" + className + kind.extension + " not found");
        }

        return object;
    }

    public void cleanup() {
        locations.clear();
    }

}
