/*
 * $Id: $
 *
 * Copyright (c) 2009 Fujitsu Danmark
 * All rights reserved.
 */

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
