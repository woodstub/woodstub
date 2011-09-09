/******************************************************************************
 * Woodstub                                                                   *
 * Copyright (c) 2011                                                         *
 * Developed by Claus Brøndby Reimer & Asbjørn Andersen                       *
 * All rights reserved                                                        *
 ******************************************************************************/

package org.wooddog.woodstub.junit;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.wooddog.demo.DataBase;
import org.wooddog.woodstub.Config;
import org.wooddog.woodstub.StreamUtil;
import org.wooddog.woodstub.compiler.Compiler;


public class StubBuilderTest {
    @Ignore
    @Test
    public void testBuilder() throws Exception, CodeDirectorException {
        CodeDirector director;
        String expected;
        String actual;

        Config.initialize(StubBuilderTest.class);

        director = CodeDirector.getInstance();
        actual = director.buildCode(DataBase.class);
        
        expected = new String(StreamUtil.read(getClass().getResourceAsStream("DataBaseStub.txt")));
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testCompilation() throws Exception, CodeDirectorException {
        org.wooddog.woodstub.compiler.Compiler compiler;
        String source;
        final byte[] code;

        source = CodeDirector.getInstance().buildCode(DataBase.class);

        compiler = new Compiler();
        compiler.addSourceFile(DataBase.class.getCanonicalName(), source);
        compiler.compile();

        code = compiler.getByteCode(DataBase.class.getCanonicalName());

        Assert.assertTrue(code.length > 0);
    }
}


