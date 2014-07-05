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

import java.lang.annotation.*;

/**
 * Classes annotated with this, will cause a new class(that implements a REST
 * service using jersey) to be automatically generated.
 *
 * @author Robert Peralta <rapito@gmail.com>
 */
@Target(ElementType.TYPE)
@Inherited
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface RestClass
{

    /**
     * Jersey Service {@link javax.ws.rs.Path} to be accessed by..
     */
    String path();

    /**
     * Package name for generated files.
     */
    String packageName() default "";

    /**
     * <i>.java</i> File to be generated.
     */
    String compiledName() default "";
}
