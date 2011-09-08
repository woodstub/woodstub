/******************************************************************************
 * Woodstub                                                                   *
 * Copyright (c) 2011                                                         *
 * Developed by Claus Brøndby Reimer & Asbjørn Andersen                       *
 * All rights reserved                                                        *
 ******************************************************************************/

package org.wooddog.woodstub.builder;

import org.wooddog.woodstub.Primitive;
import org.wooddog.woodstub.builder.elements.*;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

class MethodBuilder implements CodeBuilder {
    private Method method;
    private static final String ARG = "arg";
    private static final String VOLATILE = "volatile";
    private static final String TRANSIENT = "transient";
    private static final String VALUES = "values";
    private static final String VALUE_OF = "valueOf";

    MethodBuilder(Method method) {
        this.method = method;
    }

    public CodeElement build(CodeElement subject) {
        if (isEnumMethodToIgnore()) {
            return subject;
        }

        String modifiers = Modifier.toString(method.getModifiers());
        if (shouldBeIgnored(modifiers)) {
            return subject;
        }

        MethodElement element = createMethodElement(modifiers);
        subject.addChild(element);
        return element;
    }

    private MethodElement createMethodElement(String modifiers) {
        MethodDefinitionElement definition = createDefinitionElement(modifiers);
        MethodBodyElement body = createBodyElement(definition);
        return new MethodElement(body, definition);
    }

    private MethodDefinitionElement createDefinitionElement(String modifiers) {
        return new MethodDefinitionElement(method.getName(),
                    modifiers,
                    method.getReturnType().getCanonicalName(), method.getExceptionTypes());
    }

    private MethodBodyElement createBodyElement(MethodDefinitionElement definition) {
        MethodBodyFieldKeeper keeper = new MethodBodyFieldKeeper(method);
        String castName = getCastName();
        String[] typeNames = createArrayWithArgumentElements(definition);
        return new MethodBodyElement(keeper, typeNames, castName);
    }

    private String[] createArrayWithArgumentElements(MethodDefinitionElement definition) {
        String[] typeNames = new String[method.getParameterTypes().length];
        int varNum = 1;
        for (Class param : method.getParameterTypes()) {
            ArgumentElement arg = createArgumentAndAddToArray(typeNames, param, varNum);
            definition.addChild(arg);
            varNum++;
        }
        return typeNames;
    }

    private ArgumentElement createArgumentAndAddToArray(String[] typeNames, Class param, int varNum) {
        String typeName = param.getCanonicalName();
        ArgumentElement arg = new ArgumentElement(ARG + varNum, typeName);
        typeNames[varNum - 1] = typeName;
        return arg;
    }

    private String getCastName() {
        if (Primitive.isPrimitive(method.getReturnType())) {
            return Primitive.getWrapper(method.getReturnType()).getName();
        } else {
            return "";
        }
    }

    private boolean shouldBeIgnored(String modifiers) {
        return modifiers.contains(VOLATILE) ||
                modifiers.contains(TRANSIENT);
    }

    /**
     * If the method belongs to an enum, it must ignore the methods values() and valueOf(String)
     */
    private boolean isEnumMethodToIgnore() {
        return (method.getDeclaringClass().isEnum() &&
            (method.getName().equals(VALUES) ||
             method.getName().equals(VALUE_OF)));
    }

}