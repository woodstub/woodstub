/******************************************************************************
 * Woodstub                                                                   *
 * Copyright (c) 2011                                                         *
 * Developed by Claus Brøndby Reimer & Asbjørn Andersen                       *
 * All rights reserved                                                        *
 ******************************************************************************/

package org.wooddog.woodstub.error;

public class CodeDirectorException extends RuntimeException {
    public CodeDirectorException(CodeBuilderException source) {
        super(source);
    }
}