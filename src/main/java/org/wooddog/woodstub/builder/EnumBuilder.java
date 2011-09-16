/******************************************************************************
 * Woodstub                                                                   *
 * Copyright (c) 2011                                                         *
 * Developed by Claus Brøndby Reimer & Asbjørn Andersen                       *
 * All rights reserved                                                        *
 ******************************************************************************/

package org.wooddog.woodstub.builder;

import org.wooddog.woodstub.builder.elements.CodeElement;
import org.wooddog.woodstub.builder.elements.EnumElement;

import java.lang.reflect.Modifier;

class EnumBuilder implements CodeBuilder {
    private Class clazz;

    EnumBuilder(Class clazz) {
        this.clazz = clazz;
    }

    @Override
    public CodeElement build(CodeElement subject) throws CodeBuilderException {
        Object[] objs = clazz.getEnumConstants();
        String[] enumConstants = new String[objs.length];
        for (int i = 0; i < objs.length; i++) {
            enumConstants[i] = objs[i].toString();
        }

        EnumElement element = new EnumElement(clazz.getSimpleName(), Modifier.toString(clazz.getModifiers()), clazz.getPackage().getName(), enumConstants);
        subject.addChild(element);
        return element;
    }
}