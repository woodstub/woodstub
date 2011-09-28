/******************************************************************************
 * Woodstub                                                                   *
 * Copyright (c) 2011                                                         *
 * Developed by Claus Brøndby Reimer & Asbjørn Andersen                       *
 * All rights reserved                                                        *
 ******************************************************************************/

package org.wooddog.woodstub.generator;

import java.util.HashMap;
import java.util.Map;

class ClassCache {
    protected static int MAX_CACHED_CLASSES = 50;
    private static final Map<Class, String> cachedClasses = new HashMap<Class,String>();

    static boolean contains(Class clazz) {
        return cachedClasses.containsKey(clazz);
    }

    static String getStub(Class clazz) {
        return cachedClasses.get(clazz);
    }

    static void clear() {
        cachedClasses.clear();
    }

    static void addStubCode(Class classToStub, String code) {
        manageCacheSize();
        cachedClasses.put(classToStub,code);
    }

    static int size() {
        return cachedClasses.size();
    }

    private static void manageCacheSize() {
        if (cachedClasses.size()> MAX_CACHED_CLASSES) {
            clear();
        }
    }
}
