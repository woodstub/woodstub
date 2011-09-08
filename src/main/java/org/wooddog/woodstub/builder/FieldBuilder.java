/******************************************************************************
 * Woodstub                                                                   *
 * Copyright (c) 2011                                                         *
 * Developed by Claus Brøndby Reimer & Asbjørn Andersen                       *
 * All rights reserved                                                        *
 ******************************************************************************/

package org.wooddog.woodstub.builder;

import org.wooddog.woodstub.builder.elements.CodeElement;
import org.wooddog.woodstub.builder.elements.MemberElement;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

class FieldBuilder implements CodeBuilder {
    private Field field;

    FieldBuilder(Field field) {
        this.field = field;
    }

    public CodeElement build(CodeElement subject) {
        MemberElement element = new MemberElement(Modifier.toString(field.getModifiers()), field.getName(),
                field.getType().getCanonicalName());
        subject.addChild(element);
        return element;
    }
}