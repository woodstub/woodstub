package org.wooddog.woodstub.junit;

import org.wooddog.woodstub.builder.BuilderFactory;
import org.wooddog.woodstub.builder.CodeBuilder;
import org.wooddog.woodstub.builder.CodeBuilderException;
import org.wooddog.woodstub.builder.elements.CodeElement;
import org.wooddog.woodstub.builder.elements.RootElement;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * This class directs the building of a Java code file using Builder-implementations.
 */
public class CodeDirector {

    private static final int MAX_CLASSES = 50;
    //Used for saving generated stub-code, so we don't have to generate it several times.
    private static final Map<Class, String> classes = new HashMap<Class,String>();

    public static CodeDirector getInstance() {
        return new CodeDirector();
    }

    private CodeDirector() {
    }

    public String buildCode(Class clazz) throws CodeDirectorException {
        if (classes.containsKey(clazz)) {
            return classes.get(clazz);
        }

        if (cacheSize()>MAX_CLASSES) {
            clearCache();
        }

        RootElement root = new RootElement();
        buildCodeTree(clazz, root);
        //Get the code
        String code = root.getCode();
        classes.put(clazz,code);
        return code;
    }

    public static void cleanUp() {
        BuilderFactory.cleanup();
    }

    public static void clearCache() {
        classes.clear();
    }

    public static int cacheSize() {
       return classes.size();
    }

    private CodeElement buildCodeTree(Class clazz, RootElement root) throws CodeDirectorException {
        CodeElement classElement = root;
        try {
            classElement = buildBasicClass(clazz, classElement);
            buildFields(clazz, classElement);
            buildConstructors(clazz, classElement);
            buildMethods(clazz, classElement);
        } catch (CodeBuilderException e) {
            throw new CodeDirectorException(e);
        }

        return classElement;

    }

    private CodeElement buildBasicClass(Class clazz, CodeElement classElement) {
        CodeBuilder builder = BuilderFactory.getInstance().createBuilder(clazz);
        classElement = builder.build(classElement);
        return classElement;
    }

    private void buildFields(Class clazz, CodeElement classElement) {
        CodeBuilder builder;
        Field[] fields = clazz.getDeclaredFields();
        for (Field field:fields) {
            builder = BuilderFactory.getInstance().createBuilder(field);
            builder.build(classElement);
        }
    }

    private void buildConstructors(Class clazz, CodeElement classElement) {
        Constructor[] constructors = clazz.getDeclaredConstructors();
        boolean defaultConstructorNeeded = addAllConstructors(classElement, constructors);
        addForcedDefaultConstructor(clazz, classElement, defaultConstructorNeeded);
    }

    private boolean addAllConstructors(CodeElement classElement, Constructor[] constructors) {
        CodeBuilder builder;
        boolean defaultConstructorNeeded=true;
        for (Constructor constructor : constructors) {
            if (constructor.getParameterTypes() == null || constructor.getParameterTypes().length==0) {
                defaultConstructorNeeded=false;
            }

            builder = BuilderFactory.getInstance().createBuilder(constructor);
            builder.build(classElement);
        }

        return defaultConstructorNeeded;
    }

    private void addForcedDefaultConstructor(Class clazz, CodeElement classElement, boolean defaultConstructorNeeded) {
        CodeBuilder builder;
        if (defaultConstructorNeeded) {
            builder = BuilderFactory.getInstance().createDefaultConstructorBuilder(clazz);
            builder.build(classElement);
        }
    }

    private void buildMethods(Class clazz, CodeElement classElement) {
        CodeBuilder builder;Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
            builder = BuilderFactory.getInstance().createBuilder(method);
            builder.build(classElement);
        }
    }

}