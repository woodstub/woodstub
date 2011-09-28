/******************************************************************************
 * Woodstub                                                                   *
 * Copyright (c) 2011                                                         *
 * Developed by Claus Brøndby Reimer & Asbjørn Andersen                       *
 * All rights reserved                                                        *
 ******************************************************************************/

package org.wooddog.woodstub.generator.builder.classes;

import org.wooddog.woodstub.generator.builder.CodeBuilder;
import org.wooddog.woodstub.generator.builder.elements.ClassElement;
import org.wooddog.woodstub.generator.builder.elements.CodeElement;

public class ClassBuilder implements CodeBuilder {
    private Class clazz;

    public ClassBuilder(Class clazz) {
        this.clazz = clazz;
    }

    public CodeElement build(CodeElement root) {
        ClassElement classElement = new ClassElement(new ClassElementFieldKeeper(clazz));
        root.addChild(classElement);
        return classElement;
    }


}