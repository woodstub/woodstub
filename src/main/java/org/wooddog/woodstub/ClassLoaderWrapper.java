/******************************************************************************
 * Woodstub                                                                   *
 * Copyright (c) 2011                                                         *
 * Developed by Claus Brøndby Reimer & Asbjørn Andersen                       *
 * All rights reserved                                                        *
 ******************************************************************************/

package org.wooddog.woodstub;

public class ClassLoaderWrapper {
    private static EndorsedClassLoader classLoader;

    public static EndorsedClassLoader createNewEndorsedClassLoader(Class clazz) {
        cleanup();
        classLoader = new EndorsedClassLoader(clazz.getClassLoader());
        return classLoader;
    }

    public static void cleanup() {
        if (classLoader != null) {
            classLoader.cleanUp();
            classLoader = null;
        }
    }
}