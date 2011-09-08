/******************************************************************************
 * Woodstub                                                                   *
 * Copyright (c) 2011                                                         *
 * Developed by Claus Brøndby Reimer & Asbjørn Andersen                       *
 * All rights reserved                                                        *
 ******************************************************************************/

package org.wooddog.woodstub.builder.elements;

import org.wooddog.woodstub.Primitive;

/**
 * The body for a "forced" default constructor.
 * The only difference is that it inputs default values into a super constructor
 */
public final class DefaultConstructorBodyElement extends ConstructorBodyElement {
    private String[] defaultValues;

    public DefaultConstructorBodyElement(String className, String[] defaultValues) {
        super(className);
        this.defaultValues = defaultValues;
    }

    public String getCode() {
        StringBuilder sb = new StringBuilder();
        addCallToSuperConstructor(sb);
        sb.append(newLine);
        sb.append(getStandardCode());
        return sb.toString();
    }

    private void addCallToSuperConstructor(StringBuilder sb) {
        addSuperCallStart(sb);
        addSuperCallParameters(sb);
        addSuperCallEnd(sb);
    }

    private void addSuperCallStart(StringBuilder sb) {
        sb.append(SUPER);
    }

    private void addSuperCallParameters(StringBuilder sb) {
        for (int i=0; defaultValues != null && i< defaultValues.length;i++) {
            addDefaultValueForParameter(sb, i);
        }
    }

    private void addDefaultValueForParameter(StringBuilder sb, int i) {
        Object defaultValue = Primitive.getDefaultValue(defaultValues[i]);
        sb.append(defaultValue);
        if (isNotLast(i)) {
            sb.append(COMMA);
        }
    }

    private boolean isNotLast(int i) {
        return i< defaultValues.length-1;
    }

    private void addSuperCallEnd(StringBuilder sb) {
        sb.append(");");
    }
}