/*
 * Copyright (c) 2010.
 * Fujitsu Denmark All rights reserved
 */

package org.wooddog.woodstub;

import org.wooddog.woodstub.junit.annotation.Excludes;
import org.wooddog.woodstub.junit.annotation.Stubs;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Config {
    private static List<Class> STUB_LIST;
    private static List<String> EXCLUDE_LIST;
    private static final String CONFIG_PATH = "/woodstub-config.xml";
    private static final String WOODSTUB_CONFIG = "woodstub.config";
    private static final String OBJECT = "Object";

    /**
     * Resets the global configuration with default values and then overwrite each default value with values specified
     * by annotations in the given class.
     *
     * @param clazz indicating values to be over written.
     */
    public static void initialize(Class clazz) throws StubException {
        loadConfiguration();
        addMarkedStubs(clazz);
        addInheritedStubs(clazz);
        addExcludes(clazz);
    }

    public static List<Class> getStubs() {
        if (STUB_LIST == null) {
            STUB_LIST = new LinkedList<Class>();
        }

        return STUB_LIST;
    }

    public static List<String> getExcludedClasses() {
        if (EXCLUDE_LIST == null) {
            EXCLUDE_LIST = new LinkedList<String>();
        }

        return EXCLUDE_LIST;
    }

    public static void cleanUp() {
        if (STUB_LIST != null) {
            STUB_LIST.clear();
        }

        if (EXCLUDE_LIST != null) {
            EXCLUDE_LIST.clear();
        }

        STUB_LIST = null;
        EXCLUDE_LIST = null;
    }

    private static void loadConfiguration() {
        XmlConfigurationParser configurationParser = new XmlConfigurationParser(CONFIG_PATH,WOODSTUB_CONFIG);
        configurationParser.parse();
        STUB_LIST = new LinkedList<Class>(configurationParser.getParsedStubList());
        EXCLUDE_LIST = new LinkedList<String>(configurationParser.getParsedExcludeList());
    }

    private static void addMarkedStubs(Class clazz) {
        Stubs stubs = getStubsAnnotation(clazz);
        if (exists(stubs)) {
            STUB_LIST.clear();
            STUB_LIST.addAll(Arrays.asList(stubs.value()));
        }
    }

    private static void addInheritedStubs(Class clazz) {
        Class superClass = clazz.getSuperclass();
        if (isNotObject(superClass)) {
            addStubsFromClass(superClass);
        }
    }

    private static void addStubsFromClass(Class superClass) {
        Stubs stubs = getStubsAnnotation(superClass);
        if (exists(stubs)) {
            for (Class c : stubs.value()) {
                if (!STUB_LIST.contains(c)) {
                    STUB_LIST.add(c);
                }
            }
        }
    }

    private static void addExcludes(Class clazz) {
        Excludes excludes = getExcludesAnnotation(clazz);
        if (excludes != null) {
            EXCLUDE_LIST.clear();
            EXCLUDE_LIST.addAll(Arrays.asList(excludes.value()));
        }
    }

    private static Stubs getStubsAnnotation(Class clazz) {
        return (Stubs) clazz.getAnnotation(Stubs.class);
    }

    private static boolean isNotObject(Class superClass) {
        return !superClass.getName().endsWith(OBJECT);
    }

    private static boolean exists(Stubs stubs) {
        return stubs != null;
    }

    private static Excludes getExcludesAnnotation(Class clazz) {
        return (Excludes) clazz.getAnnotation(Excludes.class);
    }

}
