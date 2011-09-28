/******************************************************************************
 * Woodstub                                                                   *
 * Copyright (c) 2011                                                         *
 * Developed by Claus Brøndby Reimer & Asbjørn Andersen                       *
 * All rights reserved                                                        *
 ******************************************************************************/

package org.wooddog.woodstub.generator.builder.fields;

import org.wooddog.woodstub.generator.builder.CodeBuilder;
import org.wooddog.woodstub.generator.builder.elements.CodeElement;
import org.wooddog.woodstub.generator.builder.elements.MemberElement;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class FieldBuilder implements CodeBuilder {
    private Field field;

    public FieldBuilder(Field field) {
        this.field = field;
    }

    public CodeElement build(CodeElement subject) {
        MemberElement element = new MemberElement(Modifier.toString(field.getModifiers()), field.getName(),
                field.getType().getCanonicalName());
        subject.addChild(element);
        return element;
    }
}