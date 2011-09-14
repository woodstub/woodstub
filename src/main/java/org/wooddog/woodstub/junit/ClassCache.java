/******************************************************************************
 * Woodstub                                                                   *
 * Copyright (c) 2011                                                         *
 * Developed by Claus Brøndby Reimer & Asbjørn Andersen                       *
 * All rights reserved                                                        *
 ******************************************************************************/

package org.wooddog.woodstub.junit;

import java.util.HashMap;
import java.util.Map;

class ClassCache {
    protected static int MAX_CACHED_CLASSES = 50;
    private static final Map<Class, String> cachedClasses = new HashMap<Class,String>();

    public static boolean contains(Class clazz) {
        return cachedClasses.containsKey(clazz);
    }

    public static String getStub(Class clazz) {
        return cachedClasses.get(clazz);
    }

    public static void clear() {
        cachedClasses.clear();
    }

    public static void addStubCode(Class classToStub, String code) {
        manageCacheSize();
        cachedClasses.put(classToStub,code);
    }

    public static int size() {
        return cachedClasses.size();
    }

    private static void manageCacheSize() {
        if (cachedClasses.size()> MAX_CACHED_CLASSES) {
            clear();
        }
    }
}
