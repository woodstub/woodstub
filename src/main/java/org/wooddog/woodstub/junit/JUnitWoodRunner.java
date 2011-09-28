/******************************************************************************
 * Woodstub                                                                   *
 * Copyright (c) 2011                                                         *
 * Developed by Claus Brøndby Reimer & Asbjørn Andersen                       *
 * All rights reserved                                                        *
 ******************************************************************************/

package org.wooddog.woodstub.junit;

import org.junit.runner.notification.RunNotifier;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;
import org.wooddog.woodstub.hookup.ClassLoaderKeeper;
import org.wooddog.woodstub.hookup.ClassLoaderListener;
import org.wooddog.woodstub.hookup.StubCompiler;

public class JUnitWoodRunner extends BlockJUnit4ClassRunner implements ClassLoaderListener {
    private FrameworkMethod method;
    private RunNotifier notifier;

    public JUnitWoodRunner(Class<?> clazz) throws InitializationError {
        super(new StubCompiler(clazz).compileAndInitialise());
    }

    @Override
    protected void runChild(FrameworkMethod method, RunNotifier notifier) {
        this.method = method;
        this.notifier = notifier;
        ClassLoaderKeeper.requestClassLoaderLogic(this);
    }

    @Override
    public void performLogic() {
        super.runChild(method, notifier);
    }

    @Override
    public Class getSubjectClass() {
        return getTestClass().getJavaClass();
    }
}
