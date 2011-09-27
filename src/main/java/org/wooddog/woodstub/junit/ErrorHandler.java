/******************************************************************************
 * Woodstub                                                                   *
 * Copyright (c) 2011                                                         *
 * Developed by Claus Brøndby Reimer & Asbjørn Andersen                       *
 * All rights reserved                                                        *
 ******************************************************************************/

package org.wooddog.woodstub.junit;

import org.wooddog.woodstub.StubException;

class ErrorHandler {
    protected static void failFromException(String errorMessage, Throwable x) {
        throw new StubException(errorMessage, x);
    }

    protected  static  void failInCompile(String message) {
        throw new StubCompileException(message);
    }
}
