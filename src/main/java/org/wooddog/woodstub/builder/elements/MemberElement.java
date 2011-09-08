package org.wooddog.woodstub.builder.elements;

/**
 * Represents a class attribute
 */
public class MemberElement extends CodeElement {
    private final String modifier;
    private final String type;
    private final String name;
    private static final String FINAL = "final";
    private static final String FINAL_FIELDS_NOT_SUPPORTED_AT_THE_MOMENT = "//Final fields not supported at the moment (";

    public MemberElement(String modifier, String name, String type) {
        this.modifier = modifier;
        this.name = name;
        this.type = type;
    }

    @Override
    public String getCode() {
        StringBuilder builder = new StringBuilder();
        if (modifier.contains(FINAL)) {
            builder.append(FINAL_FIELDS_NOT_SUPPORTED_AT_THE_MOMENT);
            builder.append(name);
            builder.append(")\n");
            return builder.toString();
        }
        builder.append(modifier);
        builder.append(space);
        builder.append(type);
        builder.append(space);
        builder.append(name);
        builder.append(SEMICOLON);
        builder.append(newLine);
        return builder.toString();
    }
}