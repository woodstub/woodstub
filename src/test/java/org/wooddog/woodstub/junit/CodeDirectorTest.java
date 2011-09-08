/******************************************************************************
 * Woodstub                                                                   *
 * Copyright (c) 2011                                                         *
 * Developed by Claus Brøndby Reimer & Asbjørn Andersen                       *
 * All rights reserved                                                        *
 ******************************************************************************/

package org.wooddog.woodstub.junit;

import junit.framework.TestCase;
import org.junit.Test;
import org.wooddog.woodstub.builder.test.test.DataBase;


public class CodeDirectorTest extends TestCase {
    @Test
    public void testCode() throws CodeDirectorException {
        CodeDirector dir = CodeDirector.getInstance();
        CodeDirector.clearCache();
        String code = dir.buildCode(DataBase.class);
        assertEquals(1,CodeDirector.cacheSize());
        String newCode = dir.buildCode(DataBase.class);
        assertEquals(1,CodeDirector.cacheSize());
        assertEquals(code,newCode);
        CodeDirector.clearCache();
    }
}