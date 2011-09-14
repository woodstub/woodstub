/******************************************************************************
 * Woodstub                                                                   *
 * Copyright (c) 2011                                                         *
 * Developed by Claus Brøndby Reimer & Asbjørn Andersen                       *
 * All rights reserved                                                        *
 ******************************************************************************/

package org.wooddog.woodstub.builder.elements;

import org.wooddog.woodstub.builder.MethodBodyFieldKeeper;
import static org.wooddog.woodstub.builder.elements.MethodBodyCodeSnippets.*;
/**
 * The body of a method.
 * Will only contain callback related code, and specialised return statement.
 * Cannot contain children!
 */
public class MethodBodyElement extends CodeElement {
    private String className;
    protected String argumentPrefix;
    private String methodName;
    private String returnType;
    private String[] parameterTypeNames;
    private String castName;
    private Class[] exceptions;


    public MethodBodyElement(MethodBodyFieldKeeper keeper, String[] typeNames, String castName) {
        addDataFromKeeper(keeper);
        addCastName(keeper, castName);
        this.parameterTypeNames = typeNames;
    }

    protected MethodBodyElement(String className,String methodName,String returnType) {
        this.className = className;
        this.methodName=methodName;
        this.returnType=returnType;
        castName=EMPTY;
        argumentPrefix=EMPTY;
        parameterTypeNames= new String[0];
    }

    public void addChild(CodeElement e) {
        throw new IllegalArgumentException("MethodBodyElement cannot contain children!");
    }

    @Override
    public String getCode() {
        StringBuilder builder = new StringBuilder();
        addCallbackInvoke(builder);
        addNotifyCall(builder);
        addExceptionHandling(builder);
        addReturnStatement(builder);
        return builder.toString();

    }

    private void addCastName(MethodBodyFieldKeeper keeper, String castName) {
        if (keeper.isPrimitive()) {
            this.castName = castName;
        } else {
            this.castName = returnType;
        }
    }

    private void addDataFromKeeper(MethodBodyFieldKeeper keeper) {
        this.className = keeper.getClassName();
        this.argumentPrefix = keeper.getArgumentPrefix();
        this.methodName = keeper.getMethodName();
        this.returnType = keeper.getReturnType();
        this.exceptions = keeper.getExceptionTypes();
    }

    private void addCallbackInvoke(StringBuilder builder) {
        addPrologue(builder);
        addClassParameters(builder);
        addObjectParameters(builder);
    }

    private void addPrologue(StringBuilder builder) {
        builder.append(STUB_EVENT_CALL);
        builder.append("\"").append(className).append("\", ");
        builder.append("\"").append(returnType).append("\", ");
        builder.append("\"").append(methodName).append("\", ");
    }

    private void addClassParameters(StringBuilder builder) {
        if (parameterTypeNames.length > 0) {
            addParametersAddClassArray(builder);
        } else {
            builder.append("null,");
        }
    }

    private void addParametersAddClassArray(StringBuilder builder) {
        builder.append(NEW_CLASS_ARRAY);
        for (int i = 0; i < parameterTypeNames.length; i++) {
            addParameterAsClass(builder, i);
        }

        builder.append(END_BRACKET + ", ");
    }

    private void addParameterAsClass(StringBuilder builder, int i) {
        if (i != 0) {
            builder.append(", ");
        }

        builder.append(parameterTypeNames[i]).append(CLASS);
    }

    private void addObjectParameters(StringBuilder builder) {
        if (parameterTypeNames.length > 0) {
            appendParametersAsObjectArray(builder);
        } else {
            builder.append(NULL);
        }

        builder.append(newLine);
    }

    private void appendParametersAsObjectArray(StringBuilder builder) {
        builder.append(NEW_OBJECT_ARRAY);
        for (int i = 0; i < parameterTypeNames.length; i++) {
            addParameterInArray(builder, i);
        }

        builder.append(END_BRACKET + ");");
    }

    private void addParameterInArray(StringBuilder builder, int i) {
        if (i != 0) {
            builder.append(", ");
        }

        builder.append(argumentPrefix).append(i + 1);
    }

    private void addNotifyCall(StringBuilder builder) {
        builder.append(NOTIFY_CALL);
    }

    private void addExceptionHandling(StringBuilder builder) {
        addExceptionValidations(builder);
        addInvalidExceptionHandling(builder);
    }

    private void addExceptionValidations(StringBuilder builder) {
        if (exceptions != null) {
            for (Class exception : exceptions) {
                addExceptionValidation(builder, exception);
            }
        }

        builder.append(newLine);
    }

    private void addExceptionValidation(StringBuilder builder, Class exception) {
        builder.append(IF_EXCEPTION_IN_SIGNATURE).append(exception.getCanonicalName()).append("\")) {");
        builder.append(newLine);
        builder.append(THROW).append(exception.getCanonicalName()).append(CALL_GET_EXCEPTION);
        builder.append(newLine);
        builder.append(END_BRACKET);
    }

    private void addInvalidExceptionHandling(StringBuilder builder) {
        builder.append(EXCEPTION_ERROR_MSG).append(className).append(".").append(methodName).append("\");");
        builder.append(newLine);
        builder.append(END_BRACKET);
        builder.append(newLine);
    }

    private void addReturnStatement(StringBuilder builder) {
        if (methodHasReturnType()) {
            builder.append(RETURN);
            builder.append(castName);
            builder.append(')');
            builder.append(GET_RESULT);
            builder.append(newLine);
        }
    }

    private boolean methodHasReturnType() {
        return !isConstructor() && !isVoid();
    }

    private boolean isVoid() {
        return returnType.equals(VOID);
    }

    private boolean isConstructor() {
        return returnType == null || returnType.equals(EMPTY);
    }
}