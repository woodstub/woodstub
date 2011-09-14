/******************************************************************************
 * Woodstub                                                                   *
 * Copyright (c) 2011                                                         *
 * Developed by Claus Brøndby Reimer & Asbjørn Andersen                       *
 * All rights reserved                                                        *
 ******************************************************************************/

package org.wooddog.woodstub.builder;

import org.wooddog.woodstub.builder.elements.ClassElement;
import org.wooddog.woodstub.builder.elements.CodeElement;

class ClassBuilder implements CodeBuilder {
    private Class clazz;

    ClassBuilder(Class clazz) {
        this.clazz = clazz;
    }

    public CodeElement build(CodeElement root) {
        ClassElement classElement = new ClassElement(new ClassElementFieldKeeper(clazz));
        root.addChild(classElement);
        return classElement;
    }


}