/*
 * Copyright (c) 2010.
 * Fujitsu Denmark All rights reserved
 */

package org.wooddog.woodstub.assertionpoint;

import org.wooddog.woodstub.assertionpoint.logic.PointHead;

public final class AssertionPointCreator {
    /**
     * Creates a point that make assertions
     */
    public static AssertionHead expect(Class type) {
        return new PointHead(type, true);
    }

    /**
     * Creates a point that sets behavior for a matching class/method (mocking)
     */
    public static AssertionHead behaveAs(Class type) {
        return new PointHead(type, false);
    }
}