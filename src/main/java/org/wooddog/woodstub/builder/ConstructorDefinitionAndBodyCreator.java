/*
 * Copyright (c) 2011.
 * Fujitsu Denmark All rights reserved
 */

package org.wooddog.woodstub.builder;

import org.wooddog.woodstub.builder.elements.ConstructorBodyElement;
import org.wooddog.woodstub.builder.elements.ConstructorDefinitionElement;

import java.lang.reflect.Constructor;

class ConstructorDefinitionAndBodyCreator {
    private ConstructorDefinitionElement definition;
    private ConstructorBodyElement body;
    private Constructor constructor;

    ConstructorDefinitionAndBodyCreator(Constructor constructor) {
        this.constructor = constructor;
        definition = createConstructorDefinition();
        body = createConstructorBody();
    }

    public ConstructorDefinitionElement getDefinition() {
        return definition;
    }

    public ConstructorBodyElement getBody() {
        return body;
    }

    private ConstructorDefinitionElement createConstructorDefinition() {
        return new ConstructorDefinitionElement(
                getCorrectConstructorName(),
                ConstructorBuilder.PUBLIC,
                constructor.getExceptionTypes());
    }

    private String getCorrectConstructorName() {
        int indexAfterLastDot = getFormattedName().lastIndexOf(".") + 1;
        return constructor.getName().substring(indexAfterLastDot);
    }

    private ConstructorBodyElement createConstructorBody() {
        ParameterIndexer parameterIndexer = new ParameterIndexer(constructor,definition);
        String[] typeNames = parameterIndexer.getTypeNames();
        int[] indices = parameterIndexer.getIndices();
        return new ConstructorBodyElement(new MethodBodyFieldKeeper(constructor), typeNames, indices);
    }

    private String getFormattedName() {
        return constructor.getName().replace('$', '.');
    }
}
