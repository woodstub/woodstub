package org.wooddog.woodstub.builder;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class BuilderFactory {
    private static BuilderFactory INSTANCE;

    public static BuilderFactory getInstance() {
        if (INSTANCE==null) {
            INSTANCE=new BuilderFactory();
        }
        return INSTANCE;
    }

    protected BuilderFactory() {
    }

    public static void cleanup() {
        INSTANCE=null;
    }

    public CodeBuilder createBuilder(Class clazz) {
        if (clazz.isEnum()) {
            return new EnumBuilder(clazz);
        }
        return new ClassBuilder(clazz);
    }

    public CodeBuilder createBuilder(Method method) {
        return new MethodBuilder(method);
    }

    public CodeBuilder createBuilder(Field field) {
        return new FieldBuilder(field);
    }

    public CodeBuilder createBuilder(Constructor constructor) {
        return new ConstructorBuilder(constructor);
    }

    public CodeBuilder createDefaultConstructorBuilder(Class clazz) {
        return new DefaultConstructorBuilder(clazz);
    }
}