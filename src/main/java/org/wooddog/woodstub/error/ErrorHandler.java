/******************************************************************************
 * Woodstub                                                                   *
 * Copyright (c) 2011                                                         *
 * Developed by Claus Brøndby Reimer & Asbjørn Andersen                       *
 * All rights reserved                                                        *
 ******************************************************************************/

package org.wooddog.woodstub.error;

import org.wooddog.woodstub.StubException;

public class ErrorHandler {
    public static void failFromException(String errorMessage, Throwable x) {
        throw new StubException(errorMessage, x);
    }

    public static void failInCompile(String message) {
        throw new StubCompileException(message);
    }

    public static void failInBuild(String message) {
        throw new CodeBuilderException(message);
    }
}
