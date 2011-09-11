/******************************************************************************
 * Woodstub                                                                   *
 * Copyright (c) 2011                                                         *
 * Developed by Claus Brøndby Reimer & Asbjørn Andersen                       *
 * All rights reserved                                                        *
 ******************************************************************************/

package org.wooddog.woodstub.builder;

import org.wooddog.woodstub.builder.elements.CodeElement;

/**
 * CodeBuilder implementations are intended to add CodeElements to a given parent.
 * Instances are created through BuilderFactory.
 */
public interface CodeBuilder {
    /**
     * Builds a new CodeElement.
     *
     * Supplied CodeElement as parameter will get a new CodeElement attached as child.
     * New CodeElement is returned.
     * @param subject the point from which the builder will extend
     * @return The newly built CodeElement
     * @throws CodeBuilderException When illegal elements are added.
     */
    public CodeElement build(CodeElement subject) throws CodeBuilderException;
}
