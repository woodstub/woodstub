/******************************************************************************
 * Woodstub                                                                   *
 * Copyright (c) 2011                                                         *
 * Developed by Claus Brøndby Reimer & Asbjørn Andersen                       *
 * All rights reserved                                                        *
 ******************************************************************************/

package org.wooddog.woodstub.junit;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.wooddog.demo.DataBase;
import org.wooddog.demo.LoginBean;
import org.wooddog.woodstub.generator.CodeDirector;
import org.wooddog.woodstub.junit.annotation.Stubs;

@Stubs(DataBase.class)
public class ClassLoaderTest extends WoodTestCase {
    public static final Logger LOG = Logger.getLogger(ClassLoaderTest.class);

    @Test
    public void testLoader() {
        LOG.setLevel(Level.DEBUG);
        Logger.getLogger(CodeDirector.class).setLevel(Level.DEBUG);
        LOG.info("starting " + getClass().getClassLoader());

        expect(DataBase.class).toCall("getInstance").andReturnNewInstance();
        expect(DataBase.class).toCallConstructor();
        expect(DataBase.class).toCall("openConnection");
        expect(DataBase.class).toCallAnyMethod().andReturn("password");
        expect(DataBase.class).toCall("closeConnection");

        LoginBean loginBean = new LoginBean();
        loginBean.setPassword("password");
        loginBean.setUsername("username");

        Assert.assertTrue(loginBean.doLogin());
    }
}
