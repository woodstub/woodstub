/******************************************************************************
 * Woodstub                                                                   *
 * Copyright (c) 2011                                                         *
 * Developed by Claus Brøndby Reimer & Asbjørn Andersen                       *
 * All rights reserved                                                        *
 ******************************************************************************/

package org.wooddog.woodstub.junit;

import org.wooddog.woodstub.StubException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

class OriginalInvoker {
    private String className;
    private String methodName;
    private Object[] values;

    public OriginalInvoker(StubEvent event) {
        className = event.getClassName();
        methodName = event.getMethodName();
        values = event.getValues();
    }

    public Object invoke() {
        try {
            return performProxyInvocation();
        } catch (ClassNotFoundException e) {
            throw new StubException("Class not found[" + className + "]", e);
        } catch (InvocationTargetException e) {
            throw new StubException("Problem when invoking method [" + methodName + "]", e);
        } catch (InstantiationException e) {
            throw new StubException("Could not instantiate [" + className + "]", e);
        } catch (IllegalAccessException e) {
            throw new StubException("Illegal access when invoking[" + methodName + "]", e);
        }
    }

    private Object performProxyInvocation() throws ClassNotFoundException, IllegalAccessException, InstantiationException, InvocationTargetException {
        ClassLoader loader = ClassLoader.getSystemClassLoader();
        Class original = loader.loadClass(className);
        Method[] methods = original.getDeclaredMethods();
        Method methodToInvoke = findMatchingMethod(methods);
        return invokeMethod(loader, original, methodToInvoke);
    }

    private Method findMatchingMethod(Method[] methods) {
        for (Method m : methods) {
            if (nameMatches(methodName, m)) {
                return m;
            }
        }

        throw new StubException("Original method not found[" + methodName + "]");
    }

    private Object invokeMethod(ClassLoader loader, Class original,
                              Method methodToInvoke) throws InstantiationException, IllegalAccessException, ClassNotFoundException, InvocationTargetException {
        Object[] newValues = loadOriginalTypesForValues(loader);
        return methodToInvoke.invoke(original.newInstance(), newValues);
    }

    private Object[] loadOriginalTypesForValues(ClassLoader loader) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
        Object newValues[] = new Object[values.length];
        for (int i = 0; i < values.length; i++) {
            newValues[i] = loader.loadClass(values[i].getClass().getCanonicalName()).newInstance();
        }

        return newValues;
    }

    private boolean nameMatches(String methodName, Method m) {
        return m.getName().equals(methodName);
    }
}
