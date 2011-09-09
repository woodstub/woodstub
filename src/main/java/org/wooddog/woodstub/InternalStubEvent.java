/******************************************************************************
 * Woodstub                                                                   *
 * Copyright (c) 2011                                                         *
 * Developed by Claus Brøndby Reimer & Asbjørn Andersen                       *
 * All rights reserved                                                        *
 ******************************************************************************/

package org.wooddog.woodstub;

import org.wooddog.woodstub.junit.StubEvent;

public class InternalStubEvent implements StubEvent {
    private Object source;
    private final String methodName;
    private final String className;
    private Object result;
    private Throwable exception;
    private boolean redirectToRealClass;
    private ValueKeeper valueKeeper;

    /**
     * Should only be constructed through the stub-logic.
     */
    public InternalStubEvent(String className, String returnType, String methodName, Class[] types, Object[] providedValues) {
        this.className = className;
        this.methodName = methodName;
        redirectToRealClass = false;
        setDefaultReturnValue(returnType);
        initialiseValueKeeper(types, providedValues);
    }

    /**
     * Gets the boolean value, indexed by occurrence.
     *
     * @param parameterIndex The numbered occurrence of the type, starting at 1.
     * @return The specified value.
     */
    public boolean getBoolean(int parameterIndex) {
        return valueKeeper.getBoolean(parameterIndex);
    }

    /**
     * Gets the char value, indexed by occurrence.
     *
     * @param parameterIndex The numbered occurrence of the type, starting at 1.
     * @return The specified value.
     */
    public char getChar(int parameterIndex) {
        return valueKeeper.getChar(parameterIndex);
    }

    /**
     * Gets the byte value, indexed by occurrence.
     *
     * @param parameterIndex The numbered occurrence of the type, starting at 1.
     * @return The specified value.
     */
    public byte getByte(int parameterIndex) {
        return valueKeeper.getByte(parameterIndex);
    }

    /**
     * Gets the short value, indexed by occurrence.
     *
     * @param parameterIndex The numbered occurrence of the type, starting at 1.
     * @return The specified value.
     */
    public short getShort(int parameterIndex) {
        return valueKeeper.getShort(parameterIndex);
    }

    /**
     * Gets the int value, indexed by occurrence.
     *
     * @param parameterIndex The numbered occurrence of the type, starting at 1.
     * @return The specified value.
     */
    public int getInt(int parameterIndex) {
        return valueKeeper.getInt(parameterIndex);
    }

    /**
     * Gets the long value, indexed by occurrence.
     *
     * @param parameterIndex The numbered occurrence of the type, starting at 1.
     * @return The specified value.
     */
    public long getLong(int parameterIndex) {
        return valueKeeper.getLong(parameterIndex);
    }

    /**
     * Gets the float value, indexed by occurrence.
     *
     * @param parameterIndex The numbered occurrence of the type, starting at 1.
     * @return The specified value.
     */
    public float getFloat(int parameterIndex) {
        return valueKeeper.getFloat(parameterIndex);
    }

    /**
     * Gets the double value, indexed by occurrence.
     *
     * @param parameterIndex The numbered occurrence of the type, starting at 1.
     * @return The specified value.
     */
    public double getDouble(int parameterIndex) {
        return valueKeeper.getDouble(parameterIndex);
    }

    /**
     * Get the specified parameter value as an Object.
     * @param parameterIndex The index of the argument starting at 1.
     * @return
     */
    public Object getObject(int parameterIndex) {
        return valueKeeper.getObject(parameterIndex);
    }

    @Override
    public void setRedirectToRealClass(boolean redirect) {
        this.redirectToRealClass = redirect;
    }

    @Override
    public boolean getRedirectToRealClass() {
        return redirectToRealClass;
    }

    public String getClassName() {
        return className;
    }

    public String getMethodName() {
        return methodName;
    }

    public Object[] getValues() {
        return valueKeeper.getValues();
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "StubEvent{" +
                ", methodName='" + methodName + '\'' +
                ", className='" + className + '\'' +
                ", result=" + result +
                '}';
    }

    @Override
    public void raiseException(Throwable x) {
        exception = x;
    }

    public Throwable getException() {
        return exception;
    }

    private void setDefaultReturnValue(String returnType) {
        if (returnType != null) {
            result = Primitive.getDefaultValue(returnType);
        }
    }

    private void initialiseValueKeeper(Class[] types, Object[] providedValues) {
        valueKeeper = new ValueKeeper(types);
        valueKeeper.loadValuesFromArray(providedValues);
    }
}
