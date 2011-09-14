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
        CodeDirector dir = new CodeDirector();
        CodeDirector.cleanUp();
        String code = dir.buildCode(DataBase.class);
        assertEquals(1, ClassCache.size());
        String newCode = dir.buildCode(DataBase.class);
        assertEquals(1, ClassCache.size());
        assertEquals(code, newCode);
        CodeDirector.cleanUp();
    }

    @Test
    public void testCacheThreshold() {
        CodeDirector.cleanUp();
        ClassCache.MAX_CACHED_CLASSES=2;
        CodeDirector director = new CodeDirector();
        director.buildCode(OriginalInvoker.class);
        director.buildCode(WoodRunner.class);
        director.buildCode(StubCompiler.class);
        assertEquals(3,ClassCache.size());
        director.buildCode(CodeDirectorException.class);
        assertEquals(1,ClassCache.size());
    }

    private static final String SIGNATURE = "/*********************************************************************/\n"+
            "/***                     Generated with WoodStub(R)                ***/\n"+
            "/*********************************************************************/\n";
    @Test
    public void testSignature() {
        CodeDirector dir = new CodeDirector();
        CodeDirector.cleanUp();
        String code = dir.buildCode(DataBase.class);
        assertTrue(code.startsWith(SIGNATURE));
    }

}