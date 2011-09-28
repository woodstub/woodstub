/******************************************************************************
 * Woodstub                                                                   *
 * Copyright (c) 2011                                                         *
 * Developed by Claus Brøndby Reimer & Asbjørn Andersen                       *
 * All rights reserved                                                        *
 ******************************************************************************/

package org.wooddog.woodstub.generator.builder.classes;

import java.lang.reflect.Modifier;

public class ClassElementFieldKeeper {
    private static final String INTERFACE = "interface";
    private static final String CLASS = "class";
    private String[] interfaces;
    private boolean anAbstract;
    private String superClass;
    private String className;
    private String modifier;
    private String packageName;
    private String type;

    ClassElementFieldKeeper(Class clazz) {
        className = clazz.getSimpleName();
        interfaces = getInterfacesAsStrings(clazz.getInterfaces());
        anAbstract = Modifier.isAbstract(clazz.getModifiers());
        modifier = Modifier.toString(clazz.getModifiers());
        packageName = clazz.getPackage().getName();
        superClass = clazz.getSuperclass() != null ? getFormattedSuperClass(clazz) : "";
        type = clazz.isInterface() ? INTERFACE : CLASS;
    }

    private String getFormattedSuperClass(Class clazz) {
        return clazz.getSuperclass().getName().replace('$', '.');
    }

    private String[] getInterfacesAsStrings(Class[] interfaces) {
        String[] arr = new String[interfaces.length];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = interfaces[i].getName().replace('$', '.');
        }
        return arr;
    }

    public String getClassName() {
        return className;
    }

    public String[] getInterfaces() {
        return interfaces;
    }

    public boolean isAbstract() {
        return anAbstract;
    }

    public String getModifier() {
        return modifier;
    }

    public String getPackageName() {
        return packageName;
    }

    public String getSuperClass() {
        return superClass;
    }

    public String getType() {
        return type;
    }
}
