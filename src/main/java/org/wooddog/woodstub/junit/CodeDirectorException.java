/******************************************************************************
 * Woodstub                                                                   *
 * Copyright (c) 2011                                                         *
 * Developed by Claus Brøndby Reimer & Asbjørn Andersen                       *
 * All rights reserved                                                        *
 ******************************************************************************/

package org.wooddog.woodstub.junit;

import org.wooddog.woodstub.builder.CodeBuilderException;

class CodeDirectorException extends Throwable {
    public CodeDirectorException(CodeBuilderException source) {
        super(source);
    }
}