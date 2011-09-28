/******************************************************************************
 * Woodstub                                                                   *
 * Copyright (c) 2011                                                         *
 * Developed by Claus Brøndby Reimer & Asbjørn Andersen                       *
 * All rights reserved                                                        *
 ******************************************************************************/

package org.wooddog.woodstub.hookup;

import org.wooddog.woodstub.*;
import org.wooddog.woodstub.compiler.Compiler;
import org.wooddog.woodstub.error.CodeDirectorException;
import org.wooddog.woodstub.error.ErrorHandler;
import org.wooddog.woodstub.generator.CodeDirector;
import org.wooddog.woodstub.junit.WoodRunner;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class StubCompiler {
    private Class<?> clazz;
    private org.wooddog.woodstub.compiler.Compiler compiler;

    /**
     * @param clazz The class to initialise from, which can contain Stubs annotation.
     */
    public StubCompiler(Class<?> clazz) {
        this.clazz = clazz;
        compiler = new Compiler();
    }

    public Class compileAndInitialise() {
        try {
            return initialiseClassWithEndorsedLoader(clazz);
        } catch (ClassNotFoundException x) {
            ErrorHandler.failInCompile("unable to load test class\n" + x.getMessage());
        } catch (IOException x) {
            ErrorHandler.failInCompile("unable to load test class.\n" + x.getMessage());
        } catch (CodeDirectorException e) {
            ErrorHandler.failInCompile("unable to generate stubs.\n" + e.getCause());
        } finally {
            compiler.cleanup();
        }

        throw new StubException("This should not happen (StubCompiler)");
    }

    private Class initialiseClassWithEndorsedLoader(Class<?> clazz) throws IOException, ClassNotFoundException {
        Config.initialize(clazz);
        EndorsedClassLoader loader = ClassLoaderWrapper.createNewEndorsedClassLoader(WoodRunner.class);
        loadStubs(loader);
        return loader.loadClass(clazz.getCanonicalName());
    }

    private void loadStubs(EndorsedClassLoader loader) throws IOException {
        List<Class> stubs = Config.getStubs();
        if (!stubs.isEmpty()) {
            initialiseStubsFromList(loader, stubs);
        }
    }

    private void initialiseStubsFromList(EndorsedClassLoader loader, List<Class> stubs) throws IOException, CodeDirectorException {
        sortAndCompileStubs(stubs);
        swapStubsToClassLoader(loader, stubs);
    }

    private void sortAndCompileStubs(List<Class> stubs) throws IOException {
        Collections.sort(stubs, new ClassHierarchyComparator());
        compileStubs(stubs);
    }

    private void compileStubs(List<Class> stubs) throws IOException {
        CodeDirector director = new CodeDirector();
        for (Class stub : stubs) {
            compiler.addSourceFile(stub.getCanonicalName(), director.buildCode(stub));
        }

        compiler.compile();
    }

    private void swapStubsToClassLoader(EndorsedClassLoader loader,
                                               List<Class> stubs) throws IOException {
        for (Class stub : stubs) {
            String stubName = stub.getCanonicalName();
            loader.swap(stubName, compiler.getByteCode(stubName));
        }
    }
}