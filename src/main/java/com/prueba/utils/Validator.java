package com.prueba.utils;

import java.lang.reflect.Array;
import java.util.Collection;

public class Validator {

    public static boolean isEmpty(final Object object) {

        if (object == null) {
            return true;
        }

        if (object instanceof Collection<?>) {
            return ((Collection<?>) object).isEmpty();
        }

        if (object.getClass().isArray()) {
            return Array.getLength(object) == 0;
        }

        if (object instanceof CharSequence) {
            return ((CharSequence) object).length() == 0;
        }

        return false;

    }
}