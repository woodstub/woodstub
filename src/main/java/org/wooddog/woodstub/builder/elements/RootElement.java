/******************************************************************************
 * Woodstub                                                                   *
 * Copyright (c) 2011                                                         *
 * Developed by Claus Brøndby Reimer & Asbjørn Andersen                       *
 * All rights reserved                                                        *
 ******************************************************************************/

package org.wooddog.woodstub.builder.elements;

/**
 * The root element for a Java class tree.
 * Can contain ClassElement
 */
public class RootElement extends CodeElement {
    private static final String SIGNATURE = "/*********************************************************************/" + newLine +
            "/***                     Generated with WoodStub(R)                ***/" + newLine +
            "/*********************************************************************/" + newLine;

    @Override
    public String getCode() {
        StringBuilder builder = new StringBuilder();
        addSignature(builder);
        addChildren(builder);

        return builder.toString();
    }

    private void addSignature(StringBuilder builder) {
        builder.append(SIGNATURE);
        builder.append(newLine);
        builder.append(newLine);
    }

    private void addChildren(StringBuilder builder) {
        CodeElement child = getChild();
        while (child != null) {
            builder.append(child.getCode());
            child = child.getSibling();
        }
    }

    public void addChild(CodeElement e) {
        if (isValidChild(e)) {
            super.addChild(e);
            return;
        }

        throw new IllegalArgumentException("Only ClassElement or EnumElement can be added to root!");
    }

    private boolean isValidChild(CodeElement e) {
        return e instanceof ClassElement ||
                e instanceof EnumElement;
    }
}