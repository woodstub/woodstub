package org.wooddog.woodstub.builder.elements;

import org.wooddog.woodstub.builder.CodeBuilderException;

/**
 * Represents a method signature.
 * Can contain ArgumentElement
 */
public class MethodDefinitionElement extends CodeElement {
    protected String methodName;
    protected String modifier;
    protected String returnType;
    protected Class[] exceptions;
    private static final String THROWS = "throws";

    public MethodDefinitionElement(String methodName, String modifier, String returnType, Class[] exceptions) {
        this.methodName = methodName;
        this.modifier = modifier;
        this.returnType = returnType;
        this.exceptions = exceptions;
    }

    public void addChild(CodeElement e) {
        if (e instanceof ArgumentElement) {
            super.addChild(e);
            return;
        }

        throw new CodeBuilderException("Element must be an ArgumentElement");
    }

    @Override
    public String getCode() {
        StringBuilder builder = new StringBuilder();
        addSignature(builder);
        addExceptionPart(builder);
        return builder.toString();
    }

    private void addSignature(StringBuilder builder) {
        addSignatureStart(builder);
        addArguments(builder);
        addSignatureEnd(builder);
    }

    private void addSignatureStart(StringBuilder builder) {
        builder.append(modifier);
        builder.append(space);
        builder.append(returnType);
        builder.append(space);
        builder.append(methodName);
        builder.append('(');
    }

    private void addArguments(StringBuilder builder) {
        CodeElement child = getChild();
        while (child != null) {
            builder.append(child.getCode());
            child = child.getSibling();
        }
    }

    private void addSignatureEnd(StringBuilder builder) {
        builder.append(')');
        builder.append(space);
    }

    private void addExceptionPart(StringBuilder builder) {
        if (hasExceptions()) {
            addThrows(builder);
            addAllExceptions(builder);
        }
    }

    private void addAllExceptions(StringBuilder builder) {
        for (int i = 0; i < exceptions.length; i++) {
            addException(builder, i);
        }
    }

    private void addThrows(StringBuilder builder) {
        builder.append(THROWS);
        builder.append(space);
    }

    private boolean hasExceptions() {
        return exceptions != null && exceptions.length > 0;
    }

    private void addException(StringBuilder builder, int i) {
        if (isNotFirst(i)) {
            builder.append(", ");
        }

        builder.append(exceptions[i].getCanonicalName());
    }

    private boolean isNotFirst(int i) {
        return i != 0;
    }
}