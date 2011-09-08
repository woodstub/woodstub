/*
 * Copyright (c) 2011.
 * Fujitsu Denmark All rights reserved
 */

package org.wooddog.woodstub.builder;

import org.wooddog.woodstub.builder.elements.CodeElement;
import org.wooddog.woodstub.builder.elements.DefaultConstructorElement;

import java.lang.reflect.Constructor;

public class DefaultConstructorBuilder implements CodeBuilder {

    private Class clazz;

    public DefaultConstructorBuilder(Class clazz) {
        this.clazz = clazz;
    }

    @Override
    public CodeElement build(CodeElement subject) throws CodeBuilderException {
        String[] typeNames = getParameterTypesForSuperConstructor();
        DefaultConstructorElement constructor = new DefaultConstructorElement(clazz.getSimpleName(), typeNames);
        subject.addChild(constructor);
        return constructor;
    }

    private String[] getParameterTypesForSuperConstructor() {
        Constructor[] constructors = clazz.getSuperclass().getConstructors();
        Constructor constructorToUse = constructors.length > 0 ? constructors[0] : null;

        if (constructorToUse == null) {
            //The super class has no available constructors. The user must stub that as well, to give it a forced default constructor,
            //which will be invoked by *this* stub. If the superclass is not stubbed, compilation will fail.
            return new String[0];
        }

        Class[] types = constructorToUse.getParameterTypes();
        String[] typeNames = new String[types.length];

        int i = 0;
        for (Class type : types) {
            typeNames[i] = type.getSimpleName();
            i++;
        }

        return typeNames;
    }
}
