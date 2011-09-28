/******************************************************************************
 * Woodstub                                                                   *
 * Copyright (c) 2011                                                         *
 * Developed by Claus Brøndby Reimer & Asbjørn Andersen                       *
 * All rights reserved                                                        *
 ******************************************************************************/

package org.wooddog.woodstub.generator.builder.constructor;

import java.lang.reflect.Constructor;

class ConstructorParameterIndexer {
    private String[] typeNames;
    private Class[] parameterTypes;

    public ConstructorParameterIndexer(Constructor constructor, String[] typeNames) {
        this.parameterTypes = constructor.getParameterTypes();
        this.typeNames = typeNames;
    }

    public int[] getArgumentIndices() {
        int[] indices = new int[parameterTypes.length];

        for (int y = 0; y < parameterTypes.length; y++) {
            findTypeThatMatchesConstructorParameter(parameterTypes[y], indices, y);
        }

        return indices;
    }

    private void findTypeThatMatchesConstructorParameter(Class constructorType, int[] indices, int currentIndex) {
        for (int i = 0; i < typeNames.length; i++) {
            if (arrayContainsIndex(indices, i + 1)) {
                continue;
            }

            if (matchesRequiredType(typeNames[i], constructorType)) {
                indices[currentIndex] = i + 1;
                break;
            }
        }
    }

    private boolean arrayContainsIndex(int[] indices, int indexToMatch) {
        for (int x : indices) {
            if (x == indexToMatch) {
                return true;
            }
        }

        return false;
    }

    private boolean matchesRequiredType(String typeName, Class requiredType) {
        return typeName.equals(requiredType.getCanonicalName());
    }
}
