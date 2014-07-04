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
package com.fingled.jersey.annotations;

import com.sun.mirror.apt.AnnotationProcessor;
import com.sun.mirror.apt.AnnotationProcessorEnvironment;
import com.sun.mirror.apt.AnnotationProcessorFactory;
import com.sun.mirror.apt.AnnotationProcessors;
import com.sun.mirror.declaration.AnnotationTypeDeclaration;

import java.util.*;

/**
 * RestAnnotationProcessor Factory
 *
 * @author Robert Peralta <rapito@gmail.com>
 */
public class RestAnnotationProcessorFactory implements AnnotationProcessorFactory
{

    public static final String RestMethodName = "com.fingled.jersey.annotations.RestMethod";
    public static final String RestClassName = "com.fingled.jersey.annotations.RestClass";

    public Collection<String> supportedOptions()
    {
        return Collections.emptyList();
    }

    public Collection<String> supportedAnnotationTypes()
    {
        List types = new ArrayList<String>();

        types.add(RestClassName);
        types.add(RestMethodName);
        return types;
    }

    public AnnotationProcessor getProcessorFor(Set<AnnotationTypeDeclaration> set, AnnotationProcessorEnvironment ape)
    {
        AnnotationProcessor result = AnnotationProcessors.NO_OP;

        if (!set.isEmpty())
        {
            System.out.println("Building RestAnnotationProcessor!");
            result = new RestAnnotationProcessor(ape);
        }

        return result;
    }

}
