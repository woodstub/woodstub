/******************************************************************************
 * Woodstub                                                                   *
 * Copyright (c) 2011                                                         *
 * Developed by Claus Brøndby Reimer & Asbjørn Andersen                       *
 * All rights reserved                                                        *
 ******************************************************************************/

package org.wooddog.woodstub.generator.builder.elements;

/**
 * Represents a default constructor. Only used when the stubbed class doesn't have one.
 */
public final class DefaultConstructorElement extends ConstructorElement{
    private static final String ADDED_DEFAULT = "// Added Default //\n";

    public DefaultConstructorElement(String name, String[] typeNames) {
        super(new ConstructorDefinitionElement(name), new DefaultConstructorBodyElement(name,typeNames));
    }

    public String getCode() {
        return ADDED_DEFAULT +super.getCode();
    }
}