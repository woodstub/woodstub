package org.wooddog.woodstub.builder.elements;

/**
 * Subclasses often overload addChild to constrain the added types.
 */
public abstract class CodeElement{
    protected static String space = " ";
    protected static final String COMMA = ",";
    protected static final String EMPTY = "";
    protected static final char START_BRACKET = '{';
    protected static final char END_BRACKET = '}';
    protected static final char SEMICOLON = ';';
    protected static final String newLine = "\n";

    private CodeElement child;
    private CodeElement sibling;
    private CodeElement last;

    CodeElement() {
        super();
    }

    public void addChild(CodeElement e) {
        last = e;

        if (child != null) {
            child.addSibling(e);
        } else {
            child = e;
        }
    }

    CodeElement getChild() {
        return child;
    }

    public CodeElement getLast() {
        return last;
    }

    CodeElement getSibling() {
        return sibling;
    }

    private void addSibling(CodeElement e) {
        if (sibling != null) {
            sibling.addSibling(e);
        } else {
            sibling = e;
        }
    }

    public abstract String getCode();

}