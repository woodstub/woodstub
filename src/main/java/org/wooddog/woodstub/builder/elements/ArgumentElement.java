package org.wooddog.woodstub.builder.elements;

import org.wooddog.woodstub.builder.CodeBuilderException;

/**
 * Represents a parameter in a method/constructor signature.
 * When generating code, the element will take care of adding separating comma when neccesary.
 * Cannot have children.
 */
public class ArgumentElement extends CodeElement{
    private final String type;
    private final String name;

    public ArgumentElement(String name, String type) {
        this.name = name;
        this.type = type;
    }

    @Override
    public String getCode() {        
        StringBuilder builder = new StringBuilder();
        addTypeAndName(builder);
        addSeparation(builder);
        return builder.toString();
    }

    private void addTypeAndName(StringBuilder builder) {
        builder.append(type);
        builder.append(space);
        builder.append(name);
    }

    private void addSeparation(StringBuilder builder) {
        if (getSibling()!= null) {
            builder.append(',');
            builder.append(space);
        }
    }

    @Override
    public void addChild(CodeElement element) {
        throw new CodeBuilderException("ArgumentElement cannot have children.");
    }
}