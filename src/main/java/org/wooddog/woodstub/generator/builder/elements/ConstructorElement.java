/******************************************************************************
 * Woodstub                                                                   *
 * Copyright (c) 2011                                                         *
 * Developed by Claus Brøndby Reimer & Asbjørn Andersen                       *
 * All rights reserved                                                        *
 ******************************************************************************/

package org.wooddog.woodstub.generator.builder.elements;

/**
 * Can contain a MethodBodyElement and a ConstructorDefinitionElement
 */
public class ConstructorElement extends MethodElement {
    private static final String CONSTRUCTOR = "// Constructor //\n";

    public ConstructorElement(ConstructorDefinitionElement definition, ConstructorBodyElement body) {
        super(body, definition);
    }

    public String getCode() {
        StringBuilder sb = new StringBuilder(CONSTRUCTOR);
        sb.append(super.getCode());
        return sb.toString();
    }


}