/******************************************************************************
 * Woodstub                                                                   *
 * Copyright (c) 2011                                                         *
 * Developed by Claus Brøndby Reimer & Asbjørn Andersen                       *
 * All rights reserved                                                        *
 ******************************************************************************/

package org.wooddog.woodstub.assertionpoint;

public interface AssertionHead extends AssertionPoint {
    public AssertionBody toCall(String method);
    public AssertionBody toCallConstructor();
    public AssertionBody toCallAnyMethod();
}
