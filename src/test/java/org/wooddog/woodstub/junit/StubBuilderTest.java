/******************************************************************************
 * Woodstub                                                                   *
 * Copyright (c) 2011                                                         *
 * Developed by Claus Brøndby Reimer & Asbjørn Andersen                       *
 * All rights reserved                                                        *
 ******************************************************************************/

package org.wooddog.woodstub.junit;

import org.junit.Assert;
import org.junit.Test;
import org.wooddog.demo.DataBase;
import org.wooddog.woodstub.compiler.Compiler;
import org.wooddog.woodstub.generator.CodeDirector;


public class StubBuilderTest {
    @Test
    public void testCompilation() throws Exception {
        String source = new CodeDirector().buildCode(DataBase.class);

        Compiler compiler = new Compiler();
        compiler.addSourceFile(DataBase.class.getCanonicalName(), source);
        compiler.compile();

        byte[] code = compiler.getByteCode(DataBase.class.getCanonicalName());
        Assert.assertTrue(code.length>0);
    }
}


