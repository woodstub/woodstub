/******************************************************************************
 * Woodstub                                                                   *
 * Copyright (c) 2011                                                         *
 * Developed by Claus Brøndby Reimer & Asbjørn Andersen                       *
 * All rights reserved                                                        *
 ******************************************************************************/

package org.wooddog.woodstub.generator.builder.constructor;

import org.wooddog.woodstub.generator.builder.elements.ArgumentElement;
import org.wooddog.woodstub.generator.builder.elements.ConstructorDefinitionElement;
import org.wooddog.woodstub.error.CodeBuilderException;
import org.wooddog.woodstub.error.ErrorHandler;
import java.lang.reflect.Constructor;

class ConstructorParameterExpert {
    private String[] typeNames;
    private int[] indices;
    private Constructor constructor;

    ConstructorParameterExpert(Constructor constructor, ConstructorDefinitionElement definition) {
        this.constructor = constructor;
        typeNames = new String[constructor.getParameterTypes().length];
        findAndAddParameters(definition);
        findTypeIndices();
    }

    String[] getTypeNames() {
        return typeNames;
    }

    int[] getIndices() {
        return indices;
    }

    private void findAndAddParameters(ConstructorDefinitionElement definition) {
        for (int i=0; i<constructor.getParameterTypes().length;i++) {
            Class param = constructor.getParameterTypes()[i];
            addArgument(definition, param, i+1);
        }
    }

    private void addArgument(ConstructorDefinitionElement definition, Class param, int varNum) {
        ArgumentElement arg = createArgumentElement(varNum, param);
        addTypeName(varNum, param);
        definition.addChild(arg);
    }

    private ArgumentElement createArgumentElement(int varNum, Class param) {
        return new ArgumentElement(ConstructorBuilder.ARG + varNum, param.getCanonicalName());
    }

    private void addTypeName(int varNum, Class param) {
        typeNames[varNum - 1] = param.getCanonicalName();
    }

    private void findTypeIndices() {
        Constructor superConstructor = getUsableSuperConstructor();
        indices = getParameterIndices(superConstructor);
    }

    private Constructor getUsableSuperConstructor() {
        DefaultSuperConstructorFinder defaultSuperConstructorFinder = new DefaultSuperConstructorFinder(constructor.getDeclaringClass(), constructor);
        return defaultSuperConstructorFinder.findDefaultConstructor();
    }

    private int[] getParameterIndices(Constructor matchedSuper) throws CodeBuilderException {
        if (matchedSuper != null) {
            return new ConstructorParameterIndexer(matchedSuper, typeNames).getArgumentIndices();
        } else {
            failBecauseSuperConstructorCannotBeStubbed();
            return null;
        }
    }

    private void failBecauseSuperConstructorCannotBeStubbed() {
        ErrorHandler.failInBuild("Stubbed class [" + constructor.getName() + "] invoked a super-constructor without matching parameters. " +
                "To resolve: Create a new constructor.");
    }
}