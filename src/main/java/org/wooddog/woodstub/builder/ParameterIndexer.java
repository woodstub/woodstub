/******************************************************************************
 * Woodstub                                                                   *
 * Copyright (c) 2011                                                         *
 * Developed by Claus Brøndby Reimer & Asbjørn Andersen                       *
 * All rights reserved                                                        *
 ******************************************************************************/

package org.wooddog.woodstub.builder;

import org.wooddog.woodstub.builder.elements.ArgumentElement;
import org.wooddog.woodstub.builder.elements.ConstructorDefinitionElement;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;

class ParameterIndexer {
    private String[] typeNames;
    private int[] indices;
    private Constructor constructor;

    public ParameterIndexer(Constructor constructor,ConstructorDefinitionElement definition) {
        this.constructor=constructor;
        findAndAddParameters(definition);
        findTypeIndices();
    }

    public String[] getTypeNames() {
        return typeNames;
    }

    public int[] getIndices() {
        return indices;
    }

    private void findAndAddParameters(ConstructorDefinitionElement definition) {
        typeNames = new String[constructor.getParameterTypes().length];
        addParametersAsElementsOnDefinition(definition, typeNames);
    }

    private void findTypeIndices() {
        Constructor matchedSuper = getMatchingSuperConstructor();
        indices = getParameterIndices(matchedSuper, typeNames);
    }

    private Constructor getMatchingSuperConstructor() {
        Class superClass = constructor.getDeclaringClass().getSuperclass();
        Constructor[] superConstructors = superClass.getDeclaredConstructors();
        return getSuperDefaultConstructorIfExists(superClass, superConstructors);
    }

    private Constructor getSuperDefaultConstructorIfExists(Class superClass, Constructor[] superConstructors) {
        if (hasSuperClass(superClass)) {
            for (Constructor cons : superConstructors) {
                if (isDefaultConstructor(cons)) {
                    return cons;
                }
            }
        }

        return findAConstructorThatCanCallSuper(superConstructors);
    }

    private boolean hasSuperClass(Class superClass) {
        return !superClass.getCanonicalName().endsWith(ConstructorBuilder.OBJECT);
    }

    private boolean isDefaultConstructor(Constructor cons) {
        return cons.getParameterTypes().length == 0 && !Modifier.isPrivate(cons.getModifiers());
    }

    private Constructor findAConstructorThatCanCallSuper(Constructor[] superConstructors) {
        for (Constructor superConstructor : superConstructors) {
            if (isPrivateWithNoParameters(superConstructor)) {
                continue;
            }

            if (checkForMatch(constructor.getParameterTypes(), superConstructor.getParameterTypes())) {
                return superConstructor;
            }
        }

        return null;
    }

    private boolean checkForMatch(Class[] sourceTypes, Class[] superTypes) {
        if (superConstructorHasMoreParameters(sourceTypes, superTypes)) {
            return false;
        }

        int matchCount = getNumberOfMatchingTypes(sourceTypes, superTypes);
        return matchCount == superTypes.length;
    }

    private boolean isPrivateWithNoParameters(Constructor cons) {
        return Modifier.isPrivate(cons.getModifiers()) && cons.getParameterTypes().length == 0;
    }

    private int[] getParameterIndices(Constructor matchedSuper, String[] typeNames) throws CodeBuilderException {
        if (matchedSuper != null) {
            return getArgumentIndices(typeNames, matchedSuper.getParameterTypes());
        } else {
            return failBecauseSuperConstructorCannotBeStubbed();
        }
    }

    private int[] getArgumentIndices(String[] typeNames, Class[] constructorTypes) {
        int[] indices = new int[constructorTypes.length];

        for (int y = 0; y < constructorTypes.length; y++) {
            findTypeThatMatchesConstructorParameter(typeNames, constructorTypes[y], indices, y);
        }

        return indices;
    }

    private void findTypeThatMatchesConstructorParameter(String[] typeNames, Class constructorType, int[] indices, int currentIndex) {
        for (int i = 0; i < typeNames.length; i++) {
            if (arrayContains(indices, i + 1)) {
                continue;
            }

            if (matchesRequiredType(typeNames[i], constructorType)) {
                indices[currentIndex] = i + 1;
                break;
            }
        }
    }

    private boolean arrayContains(int[] indices, int i) {
        for (int x:indices) {
            if (x==i){
                return true;
            }
        }

        return false;
    }

    private boolean matchesRequiredType(String typeName, Class requiredType) {
        return typeName.equals(requiredType.getCanonicalName());
    }

    private int[] failBecauseSuperConstructorCannotBeStubbed() {
        throw new CodeBuilderException("Stubbed class [" + constructor.getName() + "] invoked a super-constructor without matching parameters. To resolve: Create a new constructor.");
    }

    private int getNumberOfMatchingTypes(Class[] sourceTypes, Class[] superTypes) {
        int matchCount = 0;
        for (Class sourceType : sourceTypes) {
            for (Class superType : superTypes) {
                if (sourceType.equals(superType)) {
                    matchCount++;
                    break;
                }
            }
        }
        return matchCount;
    }

    private boolean superConstructorHasMoreParameters(Class[] sourceTypes, Class[] superTypes) {
        return superTypes.length > sourceTypes.length;
    }

    private void addParametersAsElementsOnDefinition(ConstructorDefinitionElement definition, String[] typeNames) {
        int varNum = 1;

        for (Class param : constructor.getParameterTypes()) {
            addArgument(definition, typeNames, param, varNum);
            varNum++;
        }
    }

    private void addArgument(ConstructorDefinitionElement definition, String[] typeNames, Class param, int varNum) {
        ArgumentElement arg = createArgumentElement(varNum, param);
        addTypeName(typeNames, varNum, param);
        definition.addChild(arg);
    }

    private ArgumentElement createArgumentElement(int varNum, Class param) {
        return new ArgumentElement(ConstructorBuilder.ARG + varNum, param.getCanonicalName());
    }

    private void addTypeName(String[] typeNames, int varNum, Class param) {
        typeNames[varNum - 1] = param.getCanonicalName();
    }
}
