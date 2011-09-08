/*
 * $Id: $
 *
 * Copyright (c) 2009 Fujitsu Danmark
 * All rights reserved.
 */

package org.wooddog.woodstub.builder.elements;

public class ConstructorDefinitionElement extends MethodDefinitionElement {
    private static final String PUBLIC = "public";

    public ConstructorDefinitionElement(String constructorName,String visibility, Class[] exceptions) {
        super(constructorName, visibility, EMPTY, exceptions);
    }

    public ConstructorDefinitionElement(String constructorName) {
        this(constructorName, PUBLIC,null);
    }

    
}
