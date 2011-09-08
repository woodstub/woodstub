/*
 * $Id: $
 *
 * Copyright (c) 2009 Fujitsu Danmark
 * All rights reserved.
 */

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

    public InternalStubEvent(String className, String returnType, String methodName, Class[] types, Object[] providedValues) {
        this.className = className;
        this.methodName = methodName;
        redirectToRealClass = false;
        setDefaultReturnValue(returnType);
        initialiseValueKeeper(types, providedValues);
    }

    /**
     * Gets the boolean value represented at the given index of method arguments.
     *
     * @param parameterIndex The index of the argument starting at 1.
     * @return The the long value represented at the given parameter index.
     */
    public boolean getBoolean(int parameterIndex) {
        return valueKeeper.getBoolean(parameterIndex);
    }

    public char getChar(int parameterIndex) {
        return valueKeeper.getChar(parameterIndex);
    }

    public byte getByte(int parameterIndex) {
        return valueKeeper.getByte(parameterIndex);
    }

    public short getShort(int parameterIndex) {
        return valueKeeper.getShort(parameterIndex);
    }

    public int getInt(int parameterIndex) {
        return valueKeeper.getInt(parameterIndex);
    }

    public long getLong(int parameterIndex) {
        return valueKeeper.getLong(parameterIndex);
    }

    public float getFloat(int parameterIndex) {
        return valueKeeper.getFloat(parameterIndex);
    }

    public double getDouble(int parameterIndex) {
        return valueKeeper.getDouble(parameterIndex);
    }

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
    public Object getSource() {
        return source;
    }

    public void setSource(Object source) {
        this.source = source;
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
