/******************************************************************************
 * Woodstub                                                                   *
 * Copyright (c) 2011                                                         *
 * Developed by Claus Brøndby Reimer & Asbjørn Andersen                       *
 * All rights reserved                                                        *
 ******************************************************************************/

package org.wooddog.woodstub;

class ValueKeeper {
    int boolCount = 0;
    int charCount = 0;
    int byteCount = 0;
    int shortCount = 0;
    int intCount = 0;
    int longCount = 0;
    int floatCount = 0;
    int doubleCount = 0;

    private boolean[] booleans;
    private char[] chars;
    private byte[] bytes;
    private short[] shorts;
    private int[] ints;
    private long[] longs;
    private float[] floats;
    private double[] doubles;
    private Class[] parameters;
    private Object[] valuesToKeep;

    public ValueKeeper(Class[] types) {
        this.parameters = types;
        giveParameterValuesDefaultLength();
        initialisePrimitives(parameters);
    }

    private void giveParameterValuesDefaultLength() {
        if (parameters != null) {
            valuesToKeep = new Object[parameters.length];
        } else {
            valuesToKeep = new Object[0];
        }
    }

    public void loadValuesFromArray(Object[] providedValues) {
        setValuesFromProvidedArray(providedValues);
    }

    public boolean getBoolean(int parameterIndex) {
        return booleans[getArrayIndex(boolean.class, parameterIndex)];
    }

    public char getChar(int parameterIndex) {
        return chars[getArrayIndex(char.class, parameterIndex)];
    }

    public byte getByte(int parameterIndex) {
        return bytes[getArrayIndex(byte.class, parameterIndex)];
    }

    public short getShort(int parameterIndex) {
        return shorts[getArrayIndex(short.class, parameterIndex)];
    }

    public int getInt(int parameterIndex) {
        return ints[getArrayIndex(int.class, parameterIndex)];
    }

    public long getLong(int parameterIndex) {
        return longs[getArrayIndex(long.class, parameterIndex)];
    }

    public float getFloat(int parameterIndex) {
        return floats[getArrayIndex(float.class, parameterIndex)];
    }

    public double getDouble(int parameterIndex) {
        return doubles[getArrayIndex(double.class, parameterIndex)];
    }

    public Object getObject(int parameterIndex) {
        return valuesToKeep[parameterIndex - 1];
    }

    private void initialisePrimitives(Class[] types) {
        booleans = new boolean[count(boolean.class, types)];
        chars = new char[count(char.class, types)];
        bytes = new byte[count(byte.class, types)];
        shorts = new short[count(short.class, types)];
        ints = new int[count(int.class, types) + count(Integer.class, types)];
        longs = new long[count(long.class, types) + count(Long.class, types)];
        floats = new float[count(float.class, types) + count(Float.class, types)];
        doubles = new double[count(double.class, types) + count(Double.class, types)];
    }

    private int count(Class typeToCount, Class[] types) {
        if (types == null) {
            return 0;
        }

        return countOccurrencesOfType(typeToCount, types);
    }

    private int countOccurrencesOfType(Class typeToLookFor, Class[] types) {
        int count = 0;
        for (Class clazz : types) {
            if (clazz == typeToLookFor) {
                count++;
            }
        }

        return count;
    }

    private int getArrayIndex(Class type, int parameterIndex) {
        int found = 0;
        for (int i = 0; i < parameters.length; i++) {
            if (parameters[i].equals(type)) {
                found++;
            }

            if (found == parameterIndex) {
                return i;
            }
        }

        //Try with wrapper
        Class newType = Primitive.getWrapper(type);
        if (newType != null) {
            return getArrayIndex(newType, parameterIndex);
        }

        throw new StubException("Parameter at index[" + parameterIndex + "] was not of type[" + type.getName() + "]");
    }

    private void setValuesFromProvidedArray(Object[] providedValues) {
        for (int index = 0; (null != parameters) && (index < parameters.length); index++) {
            valuesToKeep[index]=providedValues[index];
            if (providedValues[index] == null) {
                continue;
            }

            if (parameters[index] == boolean.class || parameters[index] == Boolean.class) {
                addAsBoolean(providedValues[index]);
            }

            if (parameters[index] == char.class || parameters[index] == Character.class) {
                addAsChar(providedValues[index]);
            }

            if (parameters[index] == byte.class) {
                addAsByte(providedValues[index]);
            }

            if (parameters[index] == short.class) {
                addAsShort(providedValues[index]);
            }

            if (parameters[index] == int.class || parameters[index] == Integer.class) {
                addAsInt(providedValues[index]);
            }

            if (parameters[index] == long.class || parameters[index] == Long.class) {
                addAsLong(providedValues[index]);
            }

            if (parameters[index] == float.class || parameters[index] == Float.class) {
                addAsFloat(providedValues[index]);
            }

            if (parameters[index] == double.class || parameters[index] == Double.class) {
                addAsDouble(providedValues[index]);
            }

        }
    }

    private void addAsBoolean(Object value) {
        booleans[boolCount] = (Boolean) value;
        boolCount++;
    }

    private void addAsChar(Object value) {
        chars[charCount] = (Character) value;
        charCount++;
    }

    private void addAsByte(Object value) {
        bytes[byteCount] = (Byte) value;
        byteCount++;
    }

    private void addAsShort(Object value) {
        shorts[shortCount] = (Short) value;
        shortCount++;
    }

    private void addAsInt(Object value) {
        ints[intCount] = (Integer) value;
        intCount++;
    }

    private void addAsLong(Object value) {
        longs[longCount] = (Long) value;
        longCount++;
    }

    private void addAsFloat(Object value) {
        floats[floatCount] = (Float) value;
        floatCount++;
    }

    private void addAsDouble(Object value) {
        doubles[doubleCount] = (Double) value;
        doubleCount++;
    }

    public Object[] getValues() {
        return valuesToKeep;
    }
}
