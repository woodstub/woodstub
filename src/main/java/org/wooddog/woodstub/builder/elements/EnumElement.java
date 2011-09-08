/*
 * Copyright (c) 2011.
 * Fujitsu Denmark All rights reserved
 */

package org.wooddog.woodstub.builder.elements;

/**
 * Represents an enum type
 */
public class EnumElement extends CodeElement {
    private String enumName;
    private String packageName;
    private String modifier;
    private String[] constants;

    public EnumElement(String enumName, String modifier, String packageName, String[] constants) {
        this.constants = constants;
        this.modifier = modifier;
        this.packageName = packageName;
        this.enumName = enumName;
    }

    @Override
    public String getCode() {
        StringBuilder builder = new StringBuilder();
        addPackageDefinition(builder);
        addEnumDefinitionStart(builder);
        addConstants(builder);
        addChildren(builder);
        addEnumDefinitionEnd(builder);
        return builder.toString();
    }

    private void addEnumDefinitionStart(StringBuilder builder) {
        addModifier(builder);
        builder.append(space);
        builder.append("enum");
        builder.append(space);
        builder.append(enumName);
        builder.append(space);
        builder.append(START_BRACKET);
        builder.append(newLine);
    }

    private void addModifier(StringBuilder builder) {
        builder.append(newLine);
        if (!modifier.equals("static") && modifier.contains("final")) {
            modifier = modifier.replace("final", EMPTY);
        }

        builder.append(modifier);
    }

    private void addConstants(StringBuilder builder) {
        for (int i = 0; i < constants.length; i++) {
            addConstant(builder, i);
        }

        builder.append(';');
        builder.append(newLine);
    }

    private void addConstant(StringBuilder builder, int index) {
        builder.append(constants[index]);
        if (notTheLast(index)) {
            builder.append(COMMA);
        }
    }

    private boolean notTheLast(int i) {
        return i < constants.length - 1;
    }

    private void addChildren(StringBuilder builder) {
        CodeElement child = getChild();
        while (child != null) {
            builder.append(child.getCode());
            child = child.getSibling();
        }
    }

    private void addEnumDefinitionEnd(StringBuilder builder) {
        builder.append(newLine);
        builder.append(END_BRACKET);
    }

    private void addPackageDefinition(StringBuilder builder) {
        if (!packageName.isEmpty()) {
            builder.append("package");
            builder.append(space);
            builder.append(packageName);
            builder.append(SEMICOLON);
            builder.append(newLine);
        }
    }

    public void addChild(CodeElement e) {
        if (e instanceof ConstructorElement) return;
        if (e instanceof MethodElement ||
                e instanceof MemberElement ||
                e instanceof ClassElement) {
            super.addChild(e);
            return;
        }
        throw new IllegalArgumentException("Element had an invalid type:" + e.getClass());
    }
}