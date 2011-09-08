/*
 * Copyright (c) 2011.
 * Fujitsu Denmark All rights reserved
 */

package org.wooddog.woodstub;

public class ClassLoaderWrapper {
    private static EndorsedClassLoader classLoader;

    public static EndorsedClassLoader createNewEndorsedClassLoader(Class clazz) {
        if (classLoader != null) {
            classLoader.cleanUp();
        }

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