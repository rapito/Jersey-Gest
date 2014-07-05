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

import java.util.ArrayList;
import java.util.List;

/**
 * To handle basic structure for generated classes.
 *
 * @author Robert Peralta <rapito@gmail.com>
 */
class RestObject
{

    class Method
    {

        String path;
        String[] queryParams;
    }

    String filePath;
    String createPath;
    String servicePath;
    String packageName;
    List<Method> methods = new ArrayList<Method>();

    public void addMethod(String path, String[] queryParams)
    {
        Method m = new Method();

        m.path = path;
        m.queryParams = queryParams;

        methods.add(m);
    }

    public void print()
    {
        System.out.println("createPath: " + this.createPath);
        System.out.println("servicePath: " + this.servicePath);
        System.out.println("filePath: " + this.filePath);

        for (RestObject.Method m : this.methods)
        {
            System.out.println("methodPath: " + m.path);

            for (String s : m.queryParams)
            {
                System.out.println("\tmethodQueryParams: " + s);
            }
        }
    }
}
