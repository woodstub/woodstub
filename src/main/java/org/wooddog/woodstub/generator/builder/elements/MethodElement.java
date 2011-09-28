/******************************************************************************
 * Woodstub                                                                   *
 * Copyright (c) 2011                                                         *
 * Developed by Claus Brøndby Reimer & Asbjørn Andersen                       *
 * All rights reserved                                                        *
 ******************************************************************************/

package org.wooddog.woodstub.generator.builder.elements;

/**
 * Represents a method consisting of signature and body.
 * It can only contain MethodBodyElement and MethodDefinitionElement, and only one of each!
 */
public class MethodElement extends CodeElement {
    private MethodBodyElement body;
    private MethodDefinitionElement definition;
    private static final String ABSTRACT = "abstract";

    public MethodElement(MethodBodyElement body, MethodDefinitionElement definition) {
        this.body = body;
        this.definition = definition;
    }

    @Override
    public String getCode() {
        StringBuilder builder = new StringBuilder();
        addMethodDefinition(builder);
        addContent(builder);
        builder.append(newLine);
        return builder.toString();
    }

    private void addContent(StringBuilder builder) {
        if (isAbstract()) {
            addNoContent(builder);
        } else {
            addMethodContent(builder);
        }
    }

    private void addMethodContent(StringBuilder builder) {
        addContentStart(builder);
        addBody(builder);
        addContentEnd(builder);
    }

    private void addContentStart(StringBuilder builder) {
        builder.append(START_BRACKET);
        builder.append(newLine);
    }

    private void addBody(StringBuilder builder) {
        if (body != null) {
            builder.append(body.getCode());
        }
    }

    private void addContentEnd(StringBuilder builder) {
        builder.append(newLine);
        builder.append(END_BRACKET);
    }

    private void addNoContent(StringBuilder builder) {
        builder.append(";");
    }

    private boolean isAbstract() {
        return definition.modifier.contains(ABSTRACT);
    }

    private void addMethodDefinition(StringBuilder builder) {
        builder.append(definition.getCode());
    }
}