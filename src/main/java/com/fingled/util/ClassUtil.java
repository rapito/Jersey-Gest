/*
 * The MIT License
 *
 * Copyright 2014 Robert Peralta.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.fingled.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Utility class for Reflection-like methods
 *
 * @author Robert Peralta <rapito@gmail.com>
 */
public class ClassUtil
{

    public static Method getMethodAnnotatedWith(final Class<?> type, final Class<? extends Annotation> annotation, String fieldName, Object fieldValue)
    {
        List<Method> methods = getMethodsAnnotatedWith(type, annotation);

        for (Method m : methods)
            try
            {
                Annotation an = m.getAnnotation(annotation);
                Method anMethod = an.annotationType().getDeclaredMethod(fieldName);
                Object value = anMethod.invoke(an);

                if (value.toString().equals(fieldValue))
                    return m;

            }
            catch (Exception ex)
            {
                System.out.println(ex.getMessage());
                ex.printStackTrace();
            }
        return null;
    }

    public static List<Method> getMethodsAnnotatedWith(final Class<?> type, final Class<? extends Annotation> annotation)
    {

        final List<Method> methods = new ArrayList<Method>();

        for (Method m : type.getDeclaredMethods())
            if (m.getAnnotation(annotation) != null)
                methods.add(m);

        return methods;
    }

}
