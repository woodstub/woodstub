/******************************************************************************
 * Woodstub                                                                   *
 * Copyright (c) 2011                                                         *
 * Developed by Claus Brøndby Reimer & Asbjørn Andersen                       *
 * All rights reserved                                                        *
 ******************************************************************************/

package org.wooddog.woodstub.generator.builder;

import org.wooddog.woodstub.generator.builder.classes.ClassBuilder;
import org.wooddog.woodstub.generator.builder.constructor.ConstructorBuilder;
import org.wooddog.woodstub.generator.builder.constructor.DefaultConstructorBuilder;
import org.wooddog.woodstub.generator.builder.enums.EnumBuilder;
import org.wooddog.woodstub.generator.builder.fields.FieldBuilder;
import org.wooddog.woodstub.generator.builder.methods.MethodBuilder;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class BuilderFactory {
    public BuilderFactory() {
    }

    public CodeBuilder createBuilder(Class clazz) {
        if (clazz.isEnum()) {
            return new EnumBuilder(clazz);
        }

        return new ClassBuilder(clazz);
    }

    public CodeBuilder createBuilder(Method method) {
        return new MethodBuilder(method);
    }

    public CodeBuilder createBuilder(Field field) {
        return new FieldBuilder(field);
    }

    public CodeBuilder createBuilder(Constructor constructor) {
        return new ConstructorBuilder(constructor);
    }

    public CodeBuilder createDefaultConstructorBuilder(Class clazz) {
        return new DefaultConstructorBuilder(clazz);
    }
}