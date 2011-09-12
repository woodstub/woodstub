/******************************************************************************
 * Woodstub                                                                   *
 * Copyright (c) 2011                                                         *
 * Developed by Claus Brøndby Reimer & Asbjørn Andersen                       *
 * All rights reserved                                                        *
 ******************************************************************************/

package org.wooddog.woodstub.assertionpoint;

public class AssertionFailedException extends RuntimeException{
    public AssertionFailedException(String message) {
        super(message);
    }
}
