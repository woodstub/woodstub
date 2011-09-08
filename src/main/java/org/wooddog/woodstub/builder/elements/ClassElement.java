/******************************************************************************
 * Woodstub                                                                   *
 * Copyright (c) 2011                                                         *
 * Developed by Claus Brøndby Reimer & Asbjørn Andersen                       *
 * All rights reserved                                                        *
 ******************************************************************************/

package org.wooddog.woodstub.builder.elements;

import org.wooddog.woodstub.builder.ClassElementFieldKeeper;

/**
 * Can contain MemberElement, ConstructorElement, MethodElement and ClassElement ( for inner classes);
 */
public class ClassElement extends CodeElement {

    private String className;
    private String packageName;
    private String modifier;
    private boolean isAbstract; //TODO: Currrently not supported. How to handle?
    private String type;
    private String[] interfaces;
    private String superClass;
    private static final String PACKAGE = "package";
    private static final String STATIC = "static";
    private static final String EXTENDS = "extends";
    private static final String IMPLEMENTS = "implements";

    public ClassElement() {
        className=EMPTY;
        interfaces = new String[0];
        isAbstract=false;
        modifier=EMPTY;
        packageName=EMPTY;
        superClass=EMPTY;
        type=EMPTY;
    }

    public ClassElement(ClassElementFieldKeeper keeper) {
        className = keeper.getClassName();
        interfaces = keeper.getInterfaces();
        isAbstract = keeper.isAbstract();
        modifier = keeper.getModifier();
        packageName = keeper.getPackageName();
        superClass = keeper.getSuperClass();
        type = keeper.getType();
    }

    ClassElement(String className, String[] interfaces, boolean isAbstract, String modifier, String packageName, String superClass,
                        String type) {
        this.className=className;
        this.interfaces=interfaces;
        this.isAbstract = isAbstract;
        this.modifier=modifier;
        this.packageName=packageName;
        this.superClass=superClass;
        this.type=type;
    }

    @Override
    public String getCode() {
        StringBuilder builder = new StringBuilder();
        addPackageDefinition(builder);
        addClassPrologue(builder);
        addChildCode(builder);
        addEndOfClass(builder);
        return builder.toString();
    }

    private void addPackageDefinition(StringBuilder builder) {
        if (!packageName.isEmpty()) {
            builder.append(PACKAGE);
            builder.append(space);
            builder.append(packageName);
            builder.append(";");
            builder.append(newLine);
        }

        builder.append(newLine);
    }

    private void addClassPrologue(StringBuilder builder) {
        addModifier(builder);
        addClassDefinition(builder);
        addSuperClass(builder);
        addInterfaces(builder);
        addEndOfPrologue(builder);
    }

    private void addModifier(StringBuilder builder) {
        if (!modifier.equals(STATIC)) {
            builder.append(modifier);
        }
    }

    private void addClassDefinition(StringBuilder builder) {
        builder.append(space);
        builder.append(type);
        builder.append(space);
        builder.append(className);
        builder.append(space);
    }

    private void addSuperClass(StringBuilder builder) {
        if (hasSuperClass()) {
            builder.append(EXTENDS);
            builder.append(space);
            builder.append(superClass);
            builder.append(space);
        }
    }

    private boolean hasSuperClass() {
        return superClass != null && !superClass.isEmpty();
    }

    private void addInterfaces(StringBuilder builder) {
        if (hasInterfaces()) {
            addImplements(builder);
            addInterfaceNames(builder);
        }
    }

    private void addEndOfPrologue(StringBuilder builder) {
        builder.append(space);
        builder.append(START_BRACKET);
        builder.append(newLine);
    }

    private void addImplements(StringBuilder builder) {
        builder.append(IMPLEMENTS);
        builder.append(space);
    }

    private boolean hasInterfaces() {
        return interfaces != null && interfaces.length > 0;
    }

    private void addInterfaceNames(StringBuilder builder) {
        for (int i = 0; i < interfaces.length; i++) {
            addInterfaceName(builder, i);
        }
    }

    private void addInterfaceName(StringBuilder builder, int i) {
        builder.append(interfaces[i]);
        if (isNotLast(i)) {
            builder.append(COMMA);
        }
    }

    private boolean isNotLast(int i) {
        return i < interfaces.length - 1;
    }

    private void addChildCode(StringBuilder builder) {
        if (hasChild()) {
            CodeElement child = getChild();
            addCodeFromAllSubChildren(builder, child);
        }
    }

    private boolean hasChild() {
        return getChild() != null;
    }

    private void addCodeFromAllSubChildren(StringBuilder builder, CodeElement child) {
        while (child != null) {
            builder.append(child.getCode());
            child = child.getSibling();
        }
    }

    private void addEndOfClass(StringBuilder builder) {
        builder.append(newLine);
        builder.append(END_BRACKET);
    }

    public void addChild(CodeElement e) {
        if (isValidChild(e)) {
            super.addChild(e);
        } else {
            throw new IllegalArgumentException("Element had an invalid type");
        }
    }

    private boolean isValidChild(CodeElement e) {
        return e instanceof MethodElement ||
                e instanceof MemberElement ||
                e instanceof ConstructorElement ||
                e instanceof ClassElement;
    }
}