/******************************************************************************
 * Woodstub                                                                   *
 * Copyright (c) 2011                                                         *
 * Developed by Claus Brøndby Reimer & Asbjørn Andersen                       *
 * All rights reserved                                                        *
 ******************************************************************************/

package org.wooddog.demo;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.wooddog.woodstub.junit.*;
import org.wooddog.woodstub.junit.annotation.Stubs;

// TODO: clean up this class
@RunWith(JUnitWoodRunner.class)
@Stubs(DataBase.class)
public class LoginTest {

   @After
    public void tearDown() {
        WoodRunner.cleanup();
    }

    /**
     * @throws Exception
     */
    @Test
    public void testLoginSuccess() throws Exception {
        AssertionFlow flow;
        LoginBean loginBean;


        loginBean = new LoginBean();
        loginBean.setUsername("claus");
        loginBean.setPassword("good password");

        flow = new AssertionFlow();
        WoodRunner.addListener(flow);

        flow.add(new StubListener() {
            @Override
            public void invoked(StubEvent event) {
                Assert.assertEquals("getInstance", event.getMethodName());
                event.setResult(new DataBase());
            }
        });

        flow.add(new StubListener() {
            @Override
            public void invoked(StubEvent event) {
                Assert.assertEquals("org.wooddog.demo.DataBase", event.getMethodName());
            }
        });
        
        flow.add(new StubListener() {
            public void invoked(StubEvent event) {
                Assert.assertEquals("openConnection", event.getMethodName());
            }
        });

        flow.add(new StubListener() {
            public void invoked(StubEvent event) {
                Assert.assertEquals("queryForObject", event.getMethodName());
                Assert.assertEquals("select password from users where user = 'claus'", event.getObject(1));
                event.setResult("good password");
            }
        });

        flow.add(new StubListener() {
            public void invoked(StubEvent event) {
                Assert.assertEquals("closeConnection", event.getMethodName());
            }
        });

        flow.start();

        Assert.assertTrue(loginBean.doLogin());
        Assert.assertTrue(flow.isComplete());
    }


    @Test
    public void testLoginFailure() throws Exception {
        AssertionFlow flow;
        LoginBean loginBean;

        loginBean = new LoginBean();
        loginBean.setUsername("claus");
        loginBean.setPassword("bad password");

        flow = new AssertionFlow();
        WoodRunner.addListener(flow);

        flow.clear();

        flow.add(new StubListener() {
            @Override
            public void invoked(StubEvent event) {
                Assert.assertEquals("getInstance", event.getMethodName());
                event.setResult(new DataBase());
            }
        });

        flow.add(new StubListener() {
            @Override
            public void invoked(StubEvent event) {
                Assert.assertEquals("org.wooddog.demo.DataBase", event.getMethodName());
            }
        });

        flow.add(new StubListener() {
            public void invoked(StubEvent event) {
                Assert.assertEquals("openConnection", event.getMethodName());
            }
        });

        flow.add(new StubListener() {
            public void invoked(StubEvent event) {
                Assert.assertEquals("queryForObject", event.getMethodName());
                Assert.assertEquals("select password from users where user = 'claus'", event.getObject(1));
                event.setResult("good password");
            }
        });

        flow.add(new StubListener() {
            public void invoked(StubEvent event) {
                Assert.assertEquals("closeConnection", event.getMethodName());
            }
        });

        flow.start();

        Assert.assertFalse(loginBean.doLogin());
        Assert.assertTrue(flow.isComplete());
    }
}
