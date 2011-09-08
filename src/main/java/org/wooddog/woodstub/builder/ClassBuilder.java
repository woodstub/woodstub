package org.wooddog.woodstub.builder;

import org.wooddog.woodstub.builder.elements.ClassElement;
import org.wooddog.woodstub.builder.elements.CodeElement;

class ClassBuilder implements CodeBuilder {
    private Class clazz;

    ClassBuilder(Class clazz) {
        this.clazz = clazz;
    }

    public CodeElement build(CodeElement subject) {
        ClassElement element = new ClassElement(new ClassElementFieldKeeper(clazz));
        subject.addChild(element);
        return element;
    }


}