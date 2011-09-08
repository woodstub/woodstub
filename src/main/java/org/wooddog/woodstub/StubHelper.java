/******************************************************************************
 * Woodstub                                                                   *
 * Copyright (c) 2011                                                         *
 * Developed by Claus Brøndby Reimer & Asbjørn Andersen                       *
 * All rights reserved                                                        *
 ******************************************************************************/

package org.wooddog.woodstub;

import java.lang.reflect.Field;

public class StubHelper {
    public static <T> T newInstance(Class<T> c) {
        try {
            return c.newInstance();
        } catch (InstantiationException x) {
            throw new StubException(x);
        } catch (IllegalAccessException x) {
            throw new StubException(x);
        }
    }

    public static <T> T getObject(String fieldName, Object object) {
        Field field = getFieldFromObject(fieldName, object);
        boolean accessible = makeFieldAccessible(field);

        T value;
        try {
            value = (T) field.get(object);
        } catch (IllegalAccessException x) {
            throw new StubException(x);
        }

        returnToOriginalAccessibleState(field, accessible);
        return value;
    }

    private static Field getFieldFromObject(String fieldName, Object object) {
        try {
            return object.getClass().getField(fieldName);
        } catch (NoSuchFieldException x) {
            throw new StubException(x.getMessage(), x);
        }
    }

    private static boolean makeFieldAccessible(Field field) {
        boolean accessible= field.isAccessible();
        if (!accessible) {
            field.setAccessible(true);
        }

        return accessible;
    }

    private static void returnToOriginalAccessibleState(Field field, boolean accessible) {
        if (!accessible) {
            field.setAccessible(false);
        }
    }
}
