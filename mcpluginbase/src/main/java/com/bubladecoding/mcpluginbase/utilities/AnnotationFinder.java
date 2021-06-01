/*
 * Copyright (c) 2021 bublade
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.bubladecoding.mcpluginbase.utilities;

import org.jetbrains.annotations.Nullable;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.Objects;

public class AnnotationFinder {

    @Nullable
    public static <T extends Annotation> T findAnnotation(Parameter parameter, Class<T> annoClass) {
        if (parameter.isAnnotationPresent(annoClass)) {
            return parameter.getAnnotation(annoClass);
        }

        Class<?> paramType = parameter.getType();
        if (paramType.isPrimitive()) {
            return null;
        }

        return findAnnotation(paramType, annoClass);
    }

    @Nullable
    public static <T extends Annotation> T findAnnotation(Method method, Class<T> annoClass) {
        return method.isAnnotationPresent(annoClass) ? method.getAnnotation(annoClass) : null;
    }

    @Nullable
    public static <T extends Annotation> T findAnnotation(Class<?> clazz, Class<T> annoClass) {
        if (clazz.isAnnotationPresent(annoClass)) {
            return clazz.getAnnotation(annoClass);
        }

        return findInheritedAnnotation(clazz, annoClass);
    }

    @Nullable
    public static <T extends Annotation> T findInheritedAnnotation(Class<?> clazz, Class<T> annoClass) {
        Class<?> superclass = clazz.getSuperclass();
        if (superclass != Class.class) {
            T superAnnotation = findInheritedAnnotation2(superclass, annoClass);
            if (superAnnotation != null) {
                return superAnnotation;
            }
        }

        Class<?>[] interfaces = clazz.getInterfaces();
        return Arrays.stream(interfaces)
                .map(implementedInterface -> findInheritedAnnotation2(implementedInterface, annoClass))
                .filter(Objects::nonNull).findFirst().orElse(null);
    }

    @Nullable
    private static <T extends Annotation> T findInheritedAnnotation2(Class<?> clazz, Class<T> annoClass) {
        if (clazz == null || clazz.isPrimitive() || clazz == Class.class) {
            return null;
        }

        if (clazz.isAnnotationPresent(annoClass)) {
            return clazz.getAnnotation(annoClass);
        }

        return findInheritedAnnotation(clazz, annoClass);
    }
}
