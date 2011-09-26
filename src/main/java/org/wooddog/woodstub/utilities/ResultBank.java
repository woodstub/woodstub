/******************************************************************************
 * Woodstub                                                                   *
 * Copyright (c) 2011                                                         *
 * Developed by Claus Brøndby Reimer & Asbjørn Andersen                       *
 * All rights reserved                                                        *
 ******************************************************************************/

package org.wooddog.woodstub.utilities;

import org.wooddog.woodstub.junit.StubEvent;
import org.wooddog.woodstub.junit.StubListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ResultBank implements StubListener {
    Map<Class, ValueContainer<?>> types;

    public ResultBank() {
        types = new HashMap<Class, ValueContainer<?>>();
    }

    @Override
    public void invoked(StubEvent event) {
        ValueContainer valueContainer = types.get(event.getReturnType());
        if (valueContainer != null) {
            Object result = valueContainer.getNext();
            event.setResult(result);
        }
    }

    public void clearAll() {
        types.clear();
    }

    public void add(int i) {
        addType(Integer.class, i);
    }

    public void add(double d) {
        addType(Double.class, d);
    }

    public void add(float f) {
        addType(Float.class, f);
    }

    public void add(String str) {
        addType(String.class, str);
    }

    public void add(Object obj) {
        addType(obj.getClass(), obj);
    }

    private void addType(Class key, Object obj) {
        if (types.get(key) == null) {
            types.put(key, new ValueContainer(obj));
        } else {
            types.get(key).add(obj);
        }
    }

    class ValueContainer<T> {
        private List<T> values;
        private int currentValue;

        public ValueContainer(T value) {
            values = new ArrayList<T>();
            values.add(value);
            currentValue = 0;
        }

        public T getNext() {
            ensureIndexWithinSize();
            return values.get(currentValue++);
        }

        private void ensureIndexWithinSize() {
            if (currentValue >= values.size()) {
                currentValue=0;
            }
        }

        public void add(Object value) {
            values.add((T) value);
        }
    }
}
