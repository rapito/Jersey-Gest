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
 * All method annotated with this <b>must</b> be within
 * a class that has been annotated with {@link RestClass}.
 *
 * This method will be called by 2 methods of the same name,
 * these methods will be on a new generated class that
 * implements POST and GET jersey-method behavior for the
 * desired service.
 *
 * Generated methods will be of the form:
 *
 * <pre><code>
 * &#64;GET
 * &#64;Path(<i>pathName</i>)
 * &#64;Produces(MediaType.APPLICATION_XML)
 * public methodNameGet(&#64;QueryParam("<i>paramName[n]</i>") String paramN)
 * {
 *      return annotadedMethod(paramN);
 * }
 *
 * &#64;POST
 * &#64;Path(<i>pathName</i>)
 * &#64;Produces(MediaType.APPLICATION_XML)
 * public methodNamePost(&#64;QueryParam("<i>paramName[n]</i>") String paramN)
 * {
 *      return annotadedMethod(paramN);
 * }
 *
 * </code></pre>
 *
 * @author Robert Peralta <rapito@gmail.com>
 */
@Target(ElementType.METHOD)
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface RestMethod
{

    /**
     * Method Jersey {@link javax.ws.rs.Path}.
     */
    String path();

    /**
     * Method Jersey {@link javax.ws.rs.QueryParam}.
     */
    String[] queryParams() default "";
}
