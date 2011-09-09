/******************************************************************************
 * Woodstub                                                                   *
 * Copyright (c) 2011                                                         *
 * Developed by Claus Brøndby Reimer & Asbjørn Andersen                       *
 * All rights reserved                                                        *
 ******************************************************************************/

package org.wooddog.woodstub.misc;

import org.junit.Assert;
import org.junit.Test;
import org.wooddog.woodstub.ClassHierarchyComparator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ClassHierarchyComparatorTest {
    private List<Class> loaded;

    @Test
    public void testDependencyList() {
        List<Class> classList;

        classList = new ArrayList<Class>();
        classList.add(A1.class);
        classList.add(A2.class);
        classList.add(A3.class);
        classList.add(A4.class);
        classList.add(B1.class);
        classList.add(B2.class);
        classList.add(B3.class);
        classList.add(B4.class);
        classList.add(C1.class);
        classList.add(C2.class);
        classList.add(D1.class);

        for (int i = 0; i < 1000; i ++) {
            Collections.shuffle(classList);
            Collections.sort(classList, new ClassHierarchyComparator());

            loaded = new ArrayList<Class>();
            for (Class c : classList) {
                loaded.add(c);
                verify(c.getSuperclass());
            }
        }
        loaded=null;
    }

    public void verify(Class c) {
        if (Object.class == c) {
            return;
        }

        if (!loaded.contains(c)) {
            Assert.fail("super class " + c.getSimpleName() + " not previous loaded - " + Arrays.asList(loaded));
        }

        verify(c.getSuperclass());
    }

    /**
     * Created by Asbjørn Andersen
     * <p/>
     * User: denasa
     * Date: 12-08-2010
     * Time: 16:35:29
     */
    static class A1 {}

    /**
     * Created by Asbjørn Andersen
     * <p/>
     * User: denasa
     * Date: 12-08-2010
     * Time: 16:35:30
     */
    static class A2 extends A1 {}

    /**
     * Created by Asbjørn Andersen
     * <p/>
     * User: denasa
     * Date: 12-08-2010
     * Time: 16:35:30
     */
    static class A3 extends A2 {}

    /**
     * Created by Asbjørn Andersen
     * <p/>
     * User: denasa
     * Date: 12-08-2010
     * Time: 16:35:30
     */
    static class A4 extends A3 {}

    /**
     * Created by Asbjørn Andersen
     * <p/>
     * User: denasa
     * Date: 12-08-2010
     * Time: 16:35:30
     */
    static class B1 {}

    /**
     * Created by Asbjørn Andersen
     * <p/>
     * User: denasa
     * Date: 12-08-2010
     * Time: 16:35:30
     */
    static class B2 extends B1 {}

    /**
     * Created by Asbjørn Andersen
     * <p/>
     * User: denasa
     * Date: 12-08-2010
     * Time: 16:35:30
     */
    static class B3 extends B2 {}

    /**
     * Created by Asbjørn Andersen
     * <p/>
     * User: denasa
     * Date: 12-08-2010
     * Time: 16:35:30
     */
    static class B4 extends B3 {}

    /**
     * Created by Asbjørn Andersen
     * <p/>
     * User: denasa
     * Date: 12-08-2010
     * Time: 16:35:30
     */
    static class C1 extends B2 {}

    /**
     * Created by Asbjørn Andersen
     * <p/>
     * User: denasa
     * Date: 12-08-2010
     * Time: 16:35:30
     */
    static class C2 extends C1 {}
}

class D1 extends ClassHierarchyComparatorTest.C2 {}