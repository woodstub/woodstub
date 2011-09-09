/******************************************************************************
 * Woodstub                                                                   *
 * Copyright (c) 2011                                                         *
 * Developed by Claus Brøndby Reimer & Asbjørn Andersen                       *
 * All rights reserved                                                        *
 ******************************************************************************/

package org.wooddog.woodstub;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.wooddog.demo.DataBase;
import org.wooddog.demo.LoginBean;
import org.wooddog.woodstub.junit.StubEvent;
import org.wooddog.woodstub.junit.StubListener;
import org.wooddog.woodstub.junit.WoodTestCase;
import org.wooddog.woodstub.junit.annotation.Stubs;
import org.wooddog.woodstub.testmodel.ProxyTestInterface;
import org.wooddog.woodstub.testmodel.SomeObject;

@Stubs(DataBase.class)
public class WoodRunDemoTest extends WoodTestCase {
    private String username;

    @Override
    public void tearDown() {
        username = null;
        super.tearDown();
    }

    @Before
    public void setup() {
        behaveAs(DataBase.class).toCall("getInstance").andReturnNewInstance();

        addListener("org.wooddog.demo.DataBase", "queryForObject",
                new StubListener() {
                    @Override
                    public void invoked(StubEvent event) {
                        queryForObjectTest(event);
                    }
                }
        );

    }

    @Test
    public void testLogin() {
        username = "claus";
        LoginBean loginBean = new LoginBean();
        loginBean.setUsername(username);
        loginBean.setPassword("good password");
        Assert.assertTrue(loginBean.doLogin());
    }

    private void queryForObjectTest(StubEvent event) {
        Assert.assertEquals("select password from users where user = '" + username + "'",event.getObject(1));
        event.setResult("good password");
    }

    @Test
    public void testProxy() {
        ProxyTestInterface proxy = stub(ProxyTestInterface.class);
        behaveAs(ProxyTestInterface.class).toCall("testMethod").andReturn(1);
        assertEquals(1, proxy.testMethod("hej"));
    }

    @Test
    public void testProxyOnConcreteClass() {
        try {
            SomeObject proxy = stub(SomeObject.class);
        } catch (Throwable x) {
            assertEquals("Proxies can only be created from an Interface",x.getMessage());
        }
    }
}