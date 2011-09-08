/******************************************************************************
 * Woodstub                                                                   *
 * Copyright (c) 2011                                                         *
 * Developed by Claus Brøndby Reimer & Asbjørn Andersen                       *
 * All rights reserved                                                        *
 ******************************************************************************/

package org.wooddog.woodstub;

public class StubException extends RuntimeException {
    public StubException() {
        super();
    }

    public StubException(String message) {
        super(message);
    }

    public StubException(String message, Throwable cause) {
        super(message, cause);
    }

    public StubException(Throwable cause) {
        super(cause);
    }
}
