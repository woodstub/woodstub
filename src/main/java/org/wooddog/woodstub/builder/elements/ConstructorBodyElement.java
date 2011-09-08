/******************************************************************************
 * Woodstub                                                                   *
 * Copyright (c) 2011                                                         *
 * Developed by Claus Brøndby Reimer & Asbjørn Andersen                       *
 * All rights reserved                                                        *
 ******************************************************************************/

package org.wooddog.woodstub.builder.elements;

import org.wooddog.woodstub.builder.MethodBodyFieldKeeper;

public class ConstructorBodyElement extends MethodBodyElement {
    private int[] parameterIndices;
    protected static final String SUPER = "super(";

    public ConstructorBodyElement(MethodBodyFieldKeeper keeper, String[] parameterTypeNames, int[] parameterIndices) {
        super(keeper, parameterTypeNames, EMPTY);
        this.parameterIndices = parameterIndices;
    }

    public ConstructorBodyElement(String className) {
        super(className, className, EMPTY);
    }

    @Override
    public String getCode() {
        StringBuilder builder = new StringBuilder();
        addCallToSuperConstructor(builder);
        addStandardMethodBody(builder);
        return builder.toString();
    }

    private void addCallToSuperConstructor(StringBuilder builder) {
        if (hasParameters()) {
            addCallToSuper(builder);
        }
    }

    private void addCallToSuper(StringBuilder builder) {
        addSuperCallStart(builder);
        addParameters(builder);
        addSuperCallEnd(builder);
    }

    private void addParameters(StringBuilder builder) {
        for (int i = 0; i < parameterIndices.length; i++) {
            addParameter(builder, i);
        }
    }

    private void addParameter(StringBuilder builder, int i) {
        builder.append(argumentPrefix).append(parameterIndices[i]);
        if (isNotLast(i)) {
            builder.append(COMMA);
        }
    }

    private boolean isNotLast(int i) {
        return i < parameterIndices.length - 1;
    }

    private void addSuperCallStart(StringBuilder builder) {
        builder.append(SUPER);
    }

    private void addSuperCallEnd(StringBuilder builder) {
        builder.append(");");
        builder.append(newLine);
    }

    private boolean hasParameters() {
        return parameterIndices != null;
    }

    protected String getStandardCode() {
        return super.getCode();
    }

    private void addStandardMethodBody(StringBuilder builder) {
        builder.append(super.getCode());
    }
}