/******************************************************************************
 * Woodstub                                                                   *
 * Copyright (c) 2011                                                         *
 * Developed by Claus Brøndby Reimer & Asbjørn Andersen                       *
 * All rights reserved                                                        *
 ******************************************************************************/

package org.wooddog.woodstub;


import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EndorsedClassLoader extends ClassLoader {
    private Map<String, Class> loaded;
    private static final String CLASS = ".class";
    private static final String DOT = ".";
    private static final String SLASH = "/";
    private static final String JAVA = "java.";

    public EndorsedClassLoader(ClassLoader parent) {
        super(parent);
        loaded = new HashMap<String, Class>();
    }

    public Class getClass(String name) {
        return loaded.get(name);
    }

    public Class<?> swap(String name, byte[] code) {
        Class c;

        if (!loaded.containsKey(name)) {
            c = defineClass(name, code, 0, code.length);
            loaded.put(name, c);
        } else {
            c = loaded.get(name);
        }

        return c;
    }

    public void cleanUp() {
        if (loaded.size() > 0) {
            loaded.clear();
            loaded=null;
        }
    }

    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {
        return loadClass(name, false);
    }

    @Override
    protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
        if (!isHandledClass(name)) {
            return super.loadClass(name, resolve);
        }

        if (loaded==null) {
            loaded = new HashMap<String, Class>();
        }

        Class clazz = loaded.get(name);
        if (clazz == null) {
            clazz = reloadClass(name, clazz);
            loaded.put(name, clazz);
        }

        return clazz;
    }

    @Override
    protected void finalize() throws Throwable {
        cleanUp();
        super.finalize();
    }

    private Class reloadClass(String name, Class clazz) throws ClassNotFoundException {
        try {
            clazz = reload(name);
        } catch (IOException x) {
            throw new ClassNotFoundException("unable to load class " + name, x);
        } catch (ClassCircularityError x) {
            x.printStackTrace();
        }

        return clazz;
    }

    private Class<?> reload(String className) throws IOException {
        String resourceName = className.replace(DOT, SLASH);
        InputStream stream = getResourceAsStream(resourceName + CLASS);

        byte[] code = StreamUtil.read(stream);
        return defineClass(className, code, 0, code.length);
    }

    private boolean isHandledClass(String className) {
        if (className.startsWith(JAVA)) {
            return false;
        }

        List<String> excludedClassList = Config.getExcludedClasses();
        for (String name : excludedClassList) {
            if (className.matches(name)) {
                return false;
            }
        }

        return true;
    }
}
