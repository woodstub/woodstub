/******************************************************************************
 * Woodstub                                                                   *
 * Copyright (c) 2011                                                         *
 * Developed by Claus Brøndby Reimer & Asbjørn Andersen                       *
 * All rights reserved                                                        *
 ******************************************************************************/

package org.wooddog.woodstub.generator.builder.constructor;

import org.wooddog.woodstub.generator.builder.CodeBuilder;
import org.wooddog.woodstub.generator.builder.elements.*;
import org.wooddog.woodstub.error.CodeBuilderException;

import java.lang.reflect.Constructor;

public class ConstructorBuilder implements CodeBuilder {
    static final String PUBLIC = "public";
    static final String ARG = "arg";
    static final String OBJECT = "Object";
    private Constructor constructor;

    public ConstructorBuilder(Constructor constructor) {
        this.constructor = constructor;
    }

    public CodeElement build(CodeElement subject) throws CodeBuilderException {
        ConstructorElement element = createConstructor();
        subject.addChild(element);
        return element;
    }

    private ConstructorElement createConstructor() throws CodeBuilderException {
        ConstructorDefinitionAndBodyCreator creator = new ConstructorDefinitionAndBodyCreator(constructor);
        return new ConstructorElement(creator.getDefinition(), creator.getBody());
    }


}