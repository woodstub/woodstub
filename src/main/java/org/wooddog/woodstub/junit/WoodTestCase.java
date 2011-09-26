/******************************************************************************
 * Woodstub                                                                   *
 * Copyright (c) 2011                                                         *
 * Developed by Claus Brøndby Reimer & Asbjørn Andersen                       *
 * All rights reserved                                                        *
 ******************************************************************************/

package org.wooddog.woodstub.junit;

import junit.framework.TestCase;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.runner.RunWith;
import org.wooddog.woodstub.StubHelper;
import org.wooddog.woodstub.assertionpoint.AssertionHead;
import org.wooddog.woodstub.assertionpoint.AssertionPoint;
import org.wooddog.woodstub.assertionpoint.AssertionPointCreator;
import org.wooddog.woodstub.assertionpoint.proxy.ProxyCreator;
import org.wooddog.woodstub.utilities.ResultBank;
import java.util.LinkedList;
import java.util.List;

/**
 * To be implemented by test-classes.
 * Allows the user to add listeners for specific method names,
 * and gives fast access to AssertionPoints and Proxy-creation.
 */
@RunWith(WoodRunner.class)
public abstract class WoodTestCase extends TestCase implements StubListener {
    private List<AssertionPoint> points;
    private boolean invokeAsFlow;
    private int flowIndex = 0;
    private ResultBank resultBank;

    protected WoodTestCase() {
        points = new LinkedList<AssertionPoint>();
        WoodRunner.addListener(this);
        this.invokeAsFlow = false;
        resultBank = new ResultBank();
    }

    /**
     * Start a new non-verified AssertionPoint
     * @param type The class to give behavior
     * @return The next step
     */
    public AssertionHead behaveAs(Class type) {
        return addPoint(AssertionPointCreator.behaveAs(type));
    }

    /**
     * Start a new verified AssertionPoint
     * @param type The class to give behavior
     * @return The next step
     */
    public AssertionHead expect(Class type) {
        invokeAsFlow = true;
        return addPoint(AssertionPointCreator.expect(type));
    }

    /**
     * Creates a single Proxy-stub of an interface.
     */
    protected <T> T stub(Class<T> clazz) {
        return ProxyCreator.create(clazz);
    }

    /**
     * AfterClass cleanup. Should not be invoked manually.
     */
    @AfterClass
    public static void tearAllDown() {
        CodeDirector.cleanUp();
        WoodRunner.cleanup();
        System.gc();
    }

    /**
     * After test cleanup. Should not be invoked manually.
     */
    @After
    public void tearDown() {
        cleanUpPoints();
        resultBank.clearAll();
        System.runFinalization();
    }

    /**
     * The StubListener implementation, not to be invoked manually
     */
    public void invoked(StubEvent event) {
        if (invokeAsFlow) {
            invokePointsAsAssertionFlow(event);
        } else {
            invokePointsWithoutAssertions(event);
        }
    }

    /**
     * Adds a return value to the result bank.
     * @param value The value to use for matching return types.
     */
    protected void addValue(Object value) {
        resultBank.add(value);
    }

    /**
     * Adds a new instance of the class as a return value in the result bank.
     * @param clazz The class to add an instance of.
     */
    protected  void addValue(Class clazz) {
        resultBank.add(StubHelper.newInstance(clazz));
    }

    /**
     * Clears all values in the result bank.
     */
    protected  void clearValues() {
        resultBank.clearAll();
    }

    /**
     * Delegates all methods from a class to the result bank.
     * @param clazz The class to auto stub.
     */
    protected void autoStub(Class clazz) {
        behaveAs(clazz).toCallAnyMethod().andDelegateTo(resultBank);
    }

    /**
     * Delegates all methods from a class to the result bank.
     * @param object The object whose type will be auto stubbed.
     */
    protected  void autoStub(Object object) {
        autoStub(object.getClass());
    }

    /**
     * Add a listener for a specific method
     *
     * @param className  Name of a stubbed class (including package)
     * @param methodName Method name to listen for
     * @param listener   Implementation of StubListener
     */
    protected void addListener(String className, String methodName, StubListener listener) {
        try {
            addListener(Class.forName(className), methodName, listener);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * Add a listener for a specific method
     *
     * @param stubbedClass  A stubbed class
     * @param methodName Method name to listen for
     * @param listener   Implementation of StubListener
     */
    protected void addListener(Class stubbedClass, String methodName, StubListener listener) {
        addPoint(AssertionPointCreator.behaveAs(stubbedClass)).toCall(methodName).andDelegateTo(listener);
    }

    /**
     * Removes all assertion points, without cleaning.
     */
    protected void clearPoints() {
        points.clear();
    }

    /**
     * Cleans up AssertionPoints and removes them.
     */
    protected void cleanUpPoints() {
        for (AssertionPoint point : points) {
            point.cleanResources();
        }

        clearPoints();
    }

    private AssertionHead addPoint(AssertionHead point) {
        points.add(point);
        return point;
    }

    private void invokePointsWithoutAssertions(StubEvent event) {
        for (AssertionPoint point : points) {
            point.invoked(event);
        }
    }

    private void invokePointsAsAssertionFlow(StubEvent event) {
        StubListener listener = points.get(flowIndex++);
        if (flowIndex >= points.size()) {
            invokeAsFlow = false;
        }

        listener.invoked(event);
    }
}