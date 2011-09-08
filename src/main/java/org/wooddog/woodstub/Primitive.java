/******************************************************************************
 * Woodstub                                                                   *
 * Copyright (c) 2011                                                         *
 * Developed by Claus Brøndby Reimer & Asbjørn Andersen                       *
 * All rights reserved                                                        *
 ******************************************************************************/

package org.wooddog.woodstub;

import java.util.HashMap;
import java.util.Map;

public class Primitive {
    private static Map<Class, Class> WRAPPERS;
    private static Map<String, Object> VALUES;

    /**
     * Gets the wrapper class for a given primitive, including Arrays.
     *
     * @param primitive The primitive to get the wrapper class for.
     * @return the Wrapper class representing the primitive class.
     */
    public static Class getWrapper(Class primitive) {
        return getWrappers().get(primitive);
    }

    /**
     * Gets the default value of an uninitialized primitive.
     *
     * @param primitive The primitive class to get the default for.
     * @return The default value for the primitive class.
     */
    public static Object getDefaultValue(String primitive) {
        return getValues().get(primitive);
    }

    /**
     * Tests whether the given class is a primitive or an array of primitives.
     *
     * @param clazz The class to be tested.
     * @return true primitive otherwise false.
     */
    public static boolean isPrimitive(Class clazz) {
        return getWrappers().containsKey(clazz);
    }

    private static Map<Class, Class> getWrappers() {
        if (WRAPPERS == null) {
            initializeWrappers();
        }

        return WRAPPERS;
    }

    private static void initializeWrappers() {
        WRAPPERS =  new HashMap<Class, Class>();
        WRAPPERS.put(boolean.class, Boolean.class);
        WRAPPERS.put(char.class, Character.class);
        WRAPPERS.put(byte.class, Byte.class);
        WRAPPERS.put(short.class, Short.class);
        WRAPPERS.put(int.class, Integer.class);
        WRAPPERS.put(long.class, Long.class);
        WRAPPERS.put(float.class, Float.class);
        WRAPPERS.put(double.class, Double.class);
        WRAPPERS.put(boolean[].class, Boolean[].class);
        WRAPPERS.put(char[].class, Character[].class);
        WRAPPERS.put(byte[].class, Byte[].class);
        WRAPPERS.put(short[].class, Short[].class);
        WRAPPERS.put(int[].class, Integer[].class);
        WRAPPERS.put(long[].class, Long[].class);
        WRAPPERS.put(float[].class, Float[].class);
        WRAPPERS.put(double[].class, Double[].class);
    }

    private static Map<String, Object> getValues() {
        if (VALUES == null) {
            initializeValues();
        }

        return VALUES;
    }

    private static void initializeValues() {
        VALUES = new HashMap<String, Object>();
        VALUES.put(boolean.class.getCanonicalName(), false);
        VALUES.put(char.class.getCanonicalName(),  '\u0000');
        VALUES.put(byte.class.getCanonicalName(), (byte) 0);
        VALUES.put(short.class.getCanonicalName(), (short) 0);
        VALUES.put(int.class.getCanonicalName(),  0);
        VALUES.put(long.class.getCanonicalName(), (long) 0);
        VALUES.put(float.class.getCanonicalName(), (float) 0);
        VALUES.put(double.class.getCanonicalName(), (double) 0);
    }

}
