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
package com.fingled.jersey.services;

import com.fingled.jersey.annotations.RestClass;
import com.fingled.jersey.annotations.RestMethod;

/**
 * A simple test service class using {@link RestClass} and {@link RestMethod}
 * Annotations
 *
 * @author Robert Peralta
 */
@RestClass(path = "/wsdl", packageName = "my.services")
public class TestService
{

    //<editor-fold defaultstate="collapsed" desc="TESTS">
    @RestMethod(path = "/helloGest", queryParams
                =
                {
                    "name"
            })
    public static String helloGest(String name)
    {
        return "<xml>Hello " + name + "</xml>";
    }

    @RestMethod(path = "/welcomeGest")
    public static String welcomeGest()
    {
        return "<xml>Welcome Gest</xml>";
    }

    @RestMethod(path = "/byeGest", queryParams
                =
                {
                    "msg", "name"
            })
    public static String byeGest(String msg, String name)
    {
        return "<xml>" + msg + " " + name + "</xml>";
    }
    //</editor-fold>

}
