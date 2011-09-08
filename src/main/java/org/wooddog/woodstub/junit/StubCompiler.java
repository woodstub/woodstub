/*
 * Copyright (c) 2011.
 * Fujitsu Denmark All rights reserved
 */

package org.wooddog.woodstub.junit;

import org.junit.runners.model.InitializationError;
import org.wooddog.woodstub.ClassHierarchyComparator;
import org.wooddog.woodstub.ClassLoaderWrapper;
import org.wooddog.woodstub.Config;
import org.wooddog.woodstub.EndorsedClassLoader;
import org.wooddog.woodstub.compiler.Compiler;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

class StubCompiler {
    private Class<?> clazz;
    private org.wooddog.woodstub.compiler.Compiler compiler;

    public StubCompiler(Class<?> clazz) {
        this.clazz = clazz;
        compiler = new Compiler();
    }

    public Class compileAndInitialise() throws InitializationError {
        try {
            return initialiseClassWithEndorsedLoader(clazz);
        } catch (ClassNotFoundException x) {
            throw new InitializationError("unable to load test class\n" + x.getMessage());
        } catch (IOException x) {
            throw new InitializationError("unable to load test class.\n" + x.getMessage());
        } catch (CodeDirectorException e) {
            throw new InitializationError("unable to generate stubs.\n" + e.getCause());
        } finally {
            compiler.cleanup();
        }
    }

    private Class initialiseClassWithEndorsedLoader(Class<?> clazz) throws IOException, CodeDirectorException, ClassNotFoundException {
        Config.initialize(clazz);
        EndorsedClassLoader loader = ClassLoaderWrapper.createNewEndorsedClassLoader(WoodRunner.class);
        loadStubs(loader);
        return loader.loadClass(clazz.getCanonicalName());
    }

    private void loadStubs(EndorsedClassLoader loader) throws IOException, CodeDirectorException {
        List<Class> stubs = Config.getStubs();
        if (!stubs.isEmpty()) {
            initialiseStubsFromList(loader, stubs);
        }
    }

    private void initialiseStubsFromList(EndorsedClassLoader loader, List<Class> stubs) throws IOException, CodeDirectorException {
        sortAndCompileStubs(stubs);
        swapStubsToClassLoader(loader, stubs);
    }

    private void sortAndCompileStubs(List<Class> stubs) throws IOException, CodeDirectorException {
        Collections.sort(stubs, new ClassHierarchyComparator());
        compileStubs(stubs);
    }

    private void compileStubs(List<Class> stubs) throws IOException, CodeDirectorException {
        CodeDirector director = CodeDirector.getInstance();
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
