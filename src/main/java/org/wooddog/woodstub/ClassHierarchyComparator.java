/******************************************************************************
 * Woodstub                                                                   *
 * Copyright (c) 2011                                                         *
 * Developed by Claus Brøndby Reimer & Asbjørn Andersen                       *
 * All rights reserved                                                        *
 ******************************************************************************/

package org.wooddog.woodstub;

import java.util.Comparator;

/**
 * Sorts class based on relation ship to produce a list sequenced by dependency.
 *
 * Class'es from the top will only contain dependency downwards.
 */
public class ClassHierarchyComparator implements Comparator<Class> {
    @Override
    public int compare(Class a, Class b) {

        if (a == b) {
            return 0;
        }

        if (a.isAssignableFrom(b)) {
            return -1;
        }

        if (b.isAssignableFrom(a)) {
            return 1;
        }

        return a.getSimpleName().compareTo(b.getSimpleName());
    }
}
