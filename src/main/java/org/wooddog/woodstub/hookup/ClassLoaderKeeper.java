/******************************************************************************
 * Woodstub                                                                   *
 * Copyright (c) 2011                                                         *
 * Developed by Claus Brøndby Reimer & Asbjørn Andersen                       *
 * All rights reserved                                                        *
 ******************************************************************************/

package org.wooddog.woodstub.hookup;

import org.wooddog.woodstub.error.ErrorHandler;

public class ClassLoaderKeeper {
    private static ClassLoader originalClassLoader;

    /**
     * Initiates a callback with a switched class loader.
     * This makes sure that the original class loader is put back afterwards.
     * @param listener The listener containing the callback method.
     */
    public static void requestClassLoaderLogic(ClassLoaderListener listener) {
        try {
            takeClassLoaderFromClass(listener.getSubjectClass());
            listener.performLogic();
        } catch (Throwable x) {
            ErrorHandler.failFromException("Internal error, please report!", x);
        } finally {
            putBackOriginalClassLoader();
        }
    }

    private static void takeClassLoaderFromClass(Class testClass) {
        originalClassLoader = getOriginalContextClassLoader();
        setClassLoader(testClass.getClassLoader());
    }

    private static void putBackOriginalClassLoader() {
        setClassLoader(originalClassLoader);
    }

    private static ClassLoader getOriginalContextClassLoader() {
        return Thread.currentThread().getContextClassLoader();
    }

    private static void setClassLoader(ClassLoader classLoader) {
        Thread.currentThread().setContextClassLoader(classLoader);
    }
}
