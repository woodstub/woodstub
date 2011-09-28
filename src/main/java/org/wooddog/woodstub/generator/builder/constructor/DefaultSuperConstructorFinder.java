/******************************************************************************
 * Woodstub                                                                   *
 * Copyright (c) 2011                                                         *
 * Developed by Claus Brøndby Reimer & Asbjørn Andersen                       *
 * All rights reserved                                                        *
 ******************************************************************************/

package org.wooddog.woodstub.generator.builder.constructor;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;

class DefaultSuperConstructorFinder {
    private Class superClass;
    private Constructor[] superConstructors;
    private Constructor constructor;

    public DefaultSuperConstructorFinder(Class clazz, Constructor constructor) {
        this.superClass = clazz.getSuperclass();
        this.constructor = constructor;
        this.superConstructors = superClass.getDeclaredConstructors();
    }

    public Constructor findDefaultConstructor() {
        if (hasSuperClass()) {
            for (Constructor cons : superConstructors) {
                if (isDefaultConstructor(cons)) {
                    return cons;
                }
            }
        }

        return findAConstructorThatCanCallSuper();
    }

    private boolean hasSuperClass() {
        return !superClass.getCanonicalName().endsWith(ConstructorBuilder.OBJECT);
    }

    private boolean isDefaultConstructor(Constructor cons) {
        return cons.getParameterTypes().length == 0 && !Modifier.isPrivate(cons.getModifiers());
    }

    private Constructor findAConstructorThatCanCallSuper() {
        for (Constructor superConstructor : superConstructors) {
            if (isPrivateWithNoParameters(superConstructor)) {
                continue;
            }

            if (parametersMatch(constructor.getParameterTypes(), superConstructor.getParameterTypes())) {
                return superConstructor;
            }
        }

        return null;
    }

    private boolean isPrivateWithNoParameters(Constructor cons) {
        return Modifier.isPrivate(cons.getModifiers()) && cons.getParameterTypes().length == 0;
    }

    private boolean parametersMatch(Class[] sourceTypes, Class[] superTypes) {
        if (superConstructorHasMoreParameters(sourceTypes, superTypes)) {
            return false;
        }

        int matchCount = getNumberOfMatchingTypes(sourceTypes, superTypes);
        return matchCount == superTypes.length;
    }

    private boolean superConstructorHasMoreParameters(Class[] sourceTypes, Class[] superTypes) {
        return superTypes.length > sourceTypes.length;
    }

    private int getNumberOfMatchingTypes(Class[] sourceTypes, Class[] superTypes) {
        int matchCount = 0;
        for (Class sourceType : sourceTypes) {
            for (Class superType : superTypes) {
                if (sourceType.equals(superType)) {
                    matchCount++;
                    break;
                }
            }
        }

        return matchCount;
    }
}
