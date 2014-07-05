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

import com.fingled.util.ClassUtil;
import com.sun.mirror.apt.AnnotationProcessorEnvironment;

import java.io.*;
import java.lang.reflect.Method;

/**
 * Handles formatting and I/O for generated class containing resulting Jersey
 * REST Service
 *
 * @author Robert Peralta <rapito@gmail.com>
 */
class RestWriter
{

    public static boolean writeClass(String filename, RestObject rest, AnnotationProcessorEnvironment environment) throws Exception
    {
        BufferedWriter writer;
        if (!filename.endsWith(".java"))
        {
            String error = "[RestWriter.writeClass] RestClass Path must end with '.java' (" + filename + ") !!";
            System.err.println(error);
            environment.getMessager().printError(error);
            return false;
        }
        System.out.println("[RestWriter.writeClass] Generating Jersey Service file: " + filename);

        File file = new File(filename);

        File genFolder = file.getParentFile();
        deleteFolder(genFolder);
        genFolder.mkdir();

        if (file.exists())
        {
            file.createNewFile();

        }
        writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "UTF-8"));
        String className = classNameForFilePath(rest.filePath);
        String classNameToCreate = classNameForFilePath(rest.createPath);
        classNameToCreate = classNameToCreate
                .substring(1 + classNameToCreate.lastIndexOf("."));
        Class c = Class.forName(className);
        String pkgName = rest.packageName != null && rest.packageName.length() > 0
                ? "package " + rest.packageName
                : c.getPackage().toString();
        pkgName += ";";
        pkgName = pkgName.replaceAll("\"", "");

        writeLine(RestStrings.LICENSE, writer);
        writer.newLine();

        writeLine(pkgName, writer);

        writer.newLine();
        writeLine("import javax.ws.rs.*;", writer);
        writeLine("import javax.ws.rs.core.MediaType;", writer);
        writer.newLine();
        writeLine(RestStrings.GENERATED_COMMENT, writer);
        writeLine("@Path(" + rest.servicePath + ")", writer);
        writeLine("public class " + classNameToCreate + "\n{", writer);
        writer.newLine();
        for (RestObject.Method m : rest.methods)
        {
            writeGetMethod(m, c, writer);
            writePostMethod(m, c, writer);
        }
        newLine(3, writer);
        writeLine("}", writer);
        writer.close();
        return true;
    }

    public static String classNameForFilePath(String filepath)
    {
        String className = filepath.substring(filepath.lastIndexOf("java\\") + 5);
        className = className.replaceAll("[\\\\]", ".");
        className = className.replace(".java", "");
        return className;
    }

    private static void writeGetMethod(RestObject.Method m, Class c, BufferedWriter writer) throws IOException
    {
        writeMethod("Get", m, c, writer);
    }

    private static void writePostMethod(RestObject.Method m, Class c, BufferedWriter writer) throws IOException
    {
        writeMethod("Post", m, c, writer);
    }

    private static void writeMethod(String method, RestObject.Method m, Class c, BufferedWriter writer) throws IOException
    {
        boolean isGet = method.equalsIgnoreCase("Get");
        String operationName = m.path.substring(1);
        String params = "";
        String invokeParams = "";
        String paramType = isGet ? "Query" : "Form";

        Method classMethod = ClassUtil.getMethodAnnotatedWith(
                c, RestMethod.class, "path", m.path);

        for (int i = 0; m.queryParams != null && i < m.queryParams.length; i++)
        {
            String param = m.queryParams[i];
            String attr = param.replaceAll("\"", "");

            String paramDecl = "@" + paramType + "Param(" + param + ") String " + attr;

            if (i + 1 < m.queryParams.length)
            {
                invokeParams += attr + ", ";
                params += paramDecl + ", ";
            }
            else
            {
                params += paramDecl;
                invokeParams += attr;
            }
        }

        newLine(writer);

        writeLine("@" + method.toUpperCase(), writer, 1);
        writeLine("@Path(\"" + m.path + "\")", writer, 1);

        if (!isGet)
        {
            writeLine("@Consumes(MediaType.WILDCARD)", writer, 1);
        }

        writeLine("@Produces(MediaType.APPLICATION_XML)", writer, 1);
        writeLine("public String " + operationName + method
                + "(" + params + ")", writer, 1);
        writeLine("{", writer, 1);
        writeLine("return "
                + c.getName() + "." + classMethod.getName()
                + "(" + invokeParams + ");", writer, 2);
        writeLine("}", writer, 1);

        newLine(writer);
    }

    private static void writeLine(String str, BufferedWriter writer) throws IOException
    {
        writeLine(str, writer, 0);
    }

    private static void writeLine(String str, BufferedWriter writer, int tabs) throws IOException
    {
        String tabstr = "";

        for (int i = 0; i < tabs; i++)
        {
            tabstr += "\t";
        }

        writer.append(tabstr + str);
        writer.newLine();
    }

    private static void newLine(BufferedWriter writer) throws IOException
    {
        newLine(1, writer);
    }

    private static void newLine(int times, BufferedWriter writer) throws IOException
    {
        for (int i = 0; i < times; i++)
        {
            writer.newLine();
        }
    }

    private static void deleteFolder(File folder)
    {
        File[] files = folder.listFiles();
        if (files != null)
        { //some JVMs return null for empty dirs
            for (File f : files)
            {
                if (f.isDirectory())
                {
                    deleteFolder(f);
                }
                else
                {
                    f.delete();
                }
            }
        }
        folder.delete();
    }
}
