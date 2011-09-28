/******************************************************************************
 * Woodstub                                                                   *
 * Copyright (c) 2011                                                         *
 * Developed by Claus Brøndby Reimer & Asbjørn Andersen                       *
 * All rights reserved                                                        *
 ******************************************************************************/

package org.wooddog.woodstub.generator.builder.enums;

import org.wooddog.woodstub.error.CodeBuilderException;
import org.wooddog.woodstub.generator.builder.CodeBuilder;
import org.wooddog.woodstub.generator.builder.elements.CodeElement;
import org.wooddog.woodstub.generator.builder.elements.EnumElement;
import java.lang.reflect.Modifier;

public class EnumBuilder implements CodeBuilder {
    private Class clazz;

    public EnumBuilder(Class clazz) {
        this.clazz = clazz;
    }

    @Override
    public CodeElement build(CodeElement subject) throws CodeBuilderException {
        Object[] objs = clazz.getEnumConstants();
        String[] enumConstants = extractConstantNames(objs);

        EnumElement element = new EnumElement(clazz.getSimpleName(), Modifier.toString(clazz.getModifiers()), clazz.getPackage().getName(), enumConstants);
        subject.addChild(element);
        return element;
    }

    private String[] extractConstantNames(Object[] objs) {
        String[] enumConstants = new String[objs.length];
        for (int i = 0; i < objs.length; i++) {
            enumConstants[i] = objs[i].toString();
        }

        return enumConstants;
    }
}