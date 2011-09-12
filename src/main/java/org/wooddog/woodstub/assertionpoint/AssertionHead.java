/******************************************************************************
 * Woodstub                                                                   *
 * Copyright (c) 2011                                                         *
 * Developed by Claus Brøndby Reimer & Asbjørn Andersen                       *
 * All rights reserved                                                        *
 ******************************************************************************/

package org.wooddog.woodstub.assertionpoint;

public interface AssertionHead extends AssertionPoint {
    /**
     * Define a method to listen for.
     */
    public AssertionBody toCall(String method);

    /**
     * Listen for constructor.
     */
    public AssertionBody toCallConstructor();

    /**
     * Listen for any method.
     */
    public AssertionBody toCallAnyMethod();
}
