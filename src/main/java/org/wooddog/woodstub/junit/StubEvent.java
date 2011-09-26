/******************************************************************************
 * Woodstub                                                                   *
 * Copyright (c) 2011                                                         *
 * Developed by Claus Brøndby Reimer & Asbjørn Andersen                       *
 * All rights reserved                                                        *
 ******************************************************************************/

package org.wooddog.woodstub.junit;

public interface StubEvent {

    /**
     *
     * @return The name of the invoked class
     */
    String getClassName();

    /**
     * @return The name of the invoked method.
     */
    String getMethodName();

    /**
     * Returns a parameter as an object.
     * @param parameterIndex The index of the argument starting at 1.
     * @return The parameter value
     */
    Object getObject(int parameterIndex);

    /**
     * Gets the numbered occurrence of the type.
     *
     * @param parameterIndex The index of the argument starting at 1.
     * @return The the long value represented at the given parameter index.
     */
    boolean getBoolean(int parameterIndex);

    /**
     * Gets the numbered occurrence of the type.
     *
     * @param parameterIndex The index of the argument starting at 1.
     * @return The the long value represented at the given parameter index.
     */
    char getChar(int parameterIndex);

    /**
     * Gets the numbered occurrence of the type.
     *
     * @param parameterIndex The index of the argument starting at 1.
     * @return The the long value represented at the given parameter index.
     */
    byte getByte(int parameterIndex);

    /**
     * Gets the numbered occurrence of the type.
     *
     * @param parameterIndex The index of the argument starting at 1.
     * @return The the long value represented at the given parameter index.
     */
    short getShort(int parameterIndex);

    /**
     * Gets the numbered occurrence of the type.
     *
     * @param parameterIndex The index of the argument starting at 1.
     * @return The the long value represented at the given parameter index.
     */
    int getInt(int parameterIndex);

    /**
     * Gets the numbered occurrence of the type.
     *
     * @param parameterIndex The index of the argument starting at 1.
     * @return The the long value represented at the given parameter index.
     */
    long getLong(int parameterIndex);

    /**
     * Gets the numbered occurrence of the type.
     *
     * @param parameterIndex The index of the argument starting at 1.
     * @return The the long value represented at the given parameter index.
     */
    float getFloat(int parameterIndex);

    /**
     * Gets the numbered occurrence of the type.
     *
     * @param parameterIndex The index of the argument starting at 1.
     * @return The the long value represented at the given parameter index.
     */
    double getDouble(int parameterIndex);

    /**
     * Tells Woodstub to call the real (unstubbed class) if true.
     * @param redirect Whether to call real class or not.
     */
    void setRedirectToRealClass(boolean redirect);

    boolean getRedirectToRealClass();

    /**
     * Gets all the method parameter values.
     * Only intended for internal use.
     * @return All parameter values.
     */
    Object[] getValues();

    /**
     * Set the result to return.
     * It must match the type returned by the method.
     * If the method is void, the result will be ignored.
     * @param result The result to return
     */
    void setResult(Object result);
    Object getResult();

    /**
     * @return The class for the result type, if any.
     */
    Class getReturnType();

    /**
     * Sets an exception to raise.
     * Will only be raised when the callback returns.
     * @param x The exception to raise
     */
    void raiseException(Throwable x);
}
