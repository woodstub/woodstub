/*
 * Copyright (c) 2011.
 * Fujitsu Denmark All rights reserved
 */

package org.wooddog.woodstub.assertionpoint;

public interface AssertionHead extends AssertionPoint {
    public AssertionBody toCall(String method);
    public AssertionBody toCallConstructor();
    public AssertionBody toCallAnyMethod();
}
