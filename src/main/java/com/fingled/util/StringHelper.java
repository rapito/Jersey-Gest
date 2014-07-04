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

import java.util.HashSet;

/**
 *
 * @author Robert Peralta <rapito@gmail.com>
 */
public class StringHelper
{

    public static String toSentenceCase(String str)
    {
        if (!empty(str))
        {
            String f = str.charAt(0) + "";    //First Letter
            HashSet ups = new HashSet();
            String n = "";                  //new String

            for (int i = 0; i < str.length(); i++)
                if (Character.isUpperCase(str.charAt(i)))
                    ups.add(i);

            str = f.toUpperCase() + str.substring(1).toLowerCase();
            for (int i = 0; i < str.length(); i++)
            {
                char c = str.charAt(i);
                if (ups.contains(i))
                    c = Character.toUpperCase(c);

                n += c;
            }

            return n;
        }
        return str;
    }

    public static boolean empty(String str)
    {
        return str == null || str.length() == 0;
    }

}
