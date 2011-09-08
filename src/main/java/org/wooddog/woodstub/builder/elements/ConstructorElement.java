package org.wooddog.woodstub.builder.elements;

/**
 * Can contain a MethodBodyElement and a ConstructorDefinitionElement
 */
public class ConstructorElement extends MethodElement {
    private static final String CONSTRUCTOR = "// Constructor //\n";

    public ConstructorElement(ConstructorDefinitionElement definition, ConstructorBodyElement body) {
        super(body, definition);
    }

    public String getCode() {
        StringBuilder sb = new StringBuilder(CONSTRUCTOR);
        sb.append(super.getCode());
        return sb.toString();
    }


}