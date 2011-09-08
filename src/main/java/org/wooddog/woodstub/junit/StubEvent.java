/******************************************************************************
 * Woodstub                                                                   *
 * Copyright (c) 2011                                                         *
 * Developed by Claus Brøndby Reimer & Asbjørn Andersen                       *
 * All rights reserved                                                        *
 ******************************************************************************/

package org.wooddog.woodstub.junit;

public interface StubEvent {

    /**
     * Gets the boolean value represented at the given index of method arguments.
     *
     * @param parameterIndex The index of the argument starting at 1.
     * @return The the long value represented at the given parameter index.
     */
    boolean getBoolean(int parameterIndex);
    char getChar(int parameterIndex);
    byte getByte(int parameterIndex);
    short getShort(int parameterIndex);
    int getInt(int parameterIndex);
    long getLong(int parameterIndex);
    float getFloat(int parameterIndex);
    double getDouble(int parameterIndex);
    Object getObject(int parameterIndex);

    void setRedirectToRealClass(boolean redirect);
    boolean getRedirectToRealClass();
    String getClassName();
    String getMethodName();

    Object[] getValues();

    Object getSource();

    void setResult(Object result);

    Object getResult();
    
    void raiseException(Throwable x);
}
