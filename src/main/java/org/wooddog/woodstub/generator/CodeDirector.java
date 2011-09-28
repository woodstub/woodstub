/******************************************************************************
 * Woodstub                                                                   *
 * Copyright (c) 2011                                                         *
 * Developed by Claus Brøndby Reimer & Asbjørn Andersen                       *
 * All rights reserved                                                        *
 ******************************************************************************/

package org.wooddog.woodstub.generator;

import org.wooddog.woodstub.error.CodeBuilderException;
import org.wooddog.woodstub.error.ErrorHandler;
import org.wooddog.woodstub.generator.builder.BuilderFactory;
import org.wooddog.woodstub.generator.builder.CodeBuilder;
import org.wooddog.woodstub.generator.builder.elements.CodeElement;
import org.wooddog.woodstub.generator.builder.elements.RootElement;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * This class directs the building of a Java code file using Builder-implementations.
 */
public class CodeDirector {
    private BuilderFactory builderFactory;
    private Class classToCreateFrom;
    private CodeElement basicClass;
    private RootElement rootElement;

    /**
     * Cleans up resources.
     */
    public static void cleanUp() {
        ClassCache.clear();
    }

    public CodeDirector() {
        builderFactory = new BuilderFactory();
    }

    /**
     * Builds the stub-code for a given class.
     *
     * @param clazz The class to build stub code from.
     * @return The stub code.
     */
    public String buildCode(Class clazz) {
        if (ClassCache.contains(clazz)) {
            return ClassCache.getStub(clazz);
        }

        return createCodeFromClass(clazz);
    }

    private String createCodeFromClass(Class clazz) {
        classToCreateFrom = clazz;
        rootElement = new RootElement();
        buildCodeTree();
        return generateCode();
    }

    private void buildCodeTree() {
        try {
            createTreeOfCodeElements();
        } catch (CodeBuilderException e) {
            ErrorHandler.failFromException("Exception when building code",e);
        }
    }

    private void createTreeOfCodeElements() {
        buildBasicClass();
        buildFields();
        buildConstructors();
        buildMethods();
    }

    private void buildBasicClass() {
        basicClass = buildClass();
    }

    private CodeElement buildClass() {
        CodeBuilder builder = builderFactory.createBuilder(classToCreateFrom);
        return builder.build(rootElement);
    }

    private void buildFields() {
        Field[] fields = classToCreateFrom.getDeclaredFields();
        for (Field field:fields) {
            buildField(field);
        }
    }

    private void buildField(Field field) {
        CodeBuilder builder = builderFactory.createBuilder(field);
        builder.build(basicClass);
    }

    private void buildConstructors() {
        Constructor[] constructors = classToCreateFrom.getDeclaredConstructors();
        boolean defaultConstructorNeeded = addAllConstructors(basicClass, constructors);
        addForcedDefaultConstructor(classToCreateFrom, basicClass, defaultConstructorNeeded);
    }

    private boolean addAllConstructors(CodeElement classElement, Constructor[] constructors) {
        boolean defaultConstructorNeeded=true;
        for (Constructor constructor : constructors) {
            if (isDefaultConstructor(constructor)) {
                defaultConstructorNeeded=false;
            }

            buildConstructor(classElement, constructor);
        }

        return defaultConstructorNeeded;
    }

    private boolean isDefaultConstructor(Constructor constructor) {
        return constructor.getParameterTypes() == null || constructor.getParameterTypes().length==0;
    }

    private void buildConstructor(CodeElement classElement, Constructor constructor) {
        CodeBuilder builder = builderFactory.createBuilder(constructor);
        builder.build(classElement);
    }

    private void addForcedDefaultConstructor(Class clazz, CodeElement classElement, boolean defaultConstructorNeeded) {
        if (defaultConstructorNeeded) {
            CodeBuilder builder = builderFactory.createDefaultConstructorBuilder(clazz);
            builder.build(classElement);
        }
    }

    private void buildMethods() {
        Method[] methods = classToCreateFrom.getDeclaredMethods();
        for (Method method : methods) {
            buildMethod(method);
        }
    }

    private void buildMethod(Method method) {
        CodeBuilder builder = builderFactory.createBuilder(method);
        builder.build(basicClass);
    }

    private String generateCode() {
        String code = rootElement.getCode();
        ClassCache.addStubCode(classToCreateFrom, code);
        return code;
    }
}