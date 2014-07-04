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
import com.sun.mirror.declaration.*;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Process annotations for {@link RestClass} y {@link RestMethod}.
 *
 * @author Robert Peralta <rapito@gmail.com>
 */
public class RestAnnotationProcessor implements AnnotationProcessor
{

    private final AnnotationProcessorEnvironment environment;
    private final AnnotationTypeDeclaration methodDeclaration;
    private final AnnotationTypeDeclaration classDeclaration;

    /**
     * Map of classes to be generated.
     */
    HashMap<String, RestObject> toGenerate;

    public RestAnnotationProcessor(AnnotationProcessorEnvironment env)
    {
        environment = env;

        methodDeclaration = (AnnotationTypeDeclaration) environment
                .getTypeDeclaration(RestAnnotationProcessorFactory.RestMethodName);
        classDeclaration = (AnnotationTypeDeclaration) environment
                .getTypeDeclaration(RestAnnotationProcessorFactory.RestClassName);

        toGenerate = new HashMap<String, RestObject>();
    }

    public void process()
    {
        Collection<Declaration> classDeclarations = environment.
                getDeclarationsAnnotatedWith(classDeclaration);

        Collection<Declaration> methodDeclarations = environment.
                getDeclarationsAnnotatedWith(methodDeclaration);

        for (Declaration d : classDeclarations)
            processClassDeclaration(d);

        for (Declaration d : methodDeclarations)
            processMethodDeclaration(d);

        for (Map.Entry<String, RestObject> pair : toGenerate.entrySet())
        {
            RestObject rest = pair.getValue();
            processRestObject(rest);
        }
    }

    /**
     * Handles creation of each generated class
     *
     * @param rest Object containing necessary information to
     *             generate the class.
     */
    private void processRestObject(RestObject rest)
    {

        try
        {

            String path = rest.filePath.substring(0, rest.filePath.lastIndexOf("\\") + 1);
            String filename;

            if (rest.createPath == null)
            {
                filename = rest.filePath.substring(path.length(), rest.filePath.length());
                filename = filename.replace(".java", "Impl.java");

            }
            else
                filename = rest.createPath.replaceAll("\"", "");

            path += "generated\\";
            rest.createPath = path + filename;
            System.out.println("FullPath: " + rest.createPath);

            RestWriter.writeClass(rest.createPath, rest, environment);

        }
        catch (Exception ex)
        {
            System.err.println("Error: " + ex.getMessage());
            environment.getMessager().printError(ex.getMessage());
            Logger.getLogger(RestAnnotationProcessor.class.getSimpleName()).log(Level.SEVERE, ex.getMessage(), ex);
        }
    }

    private void processClassDeclaration(Declaration d)
    {
        addToRestObject(d, true);
    }

    private void processMethodDeclaration(Declaration d)
    {
        addToRestObject(d, false);
    }

    /**
     * Adds a new entry to the RestObject map. If already exists,
     * adds information of methods which are annotated by any
     * <i>RestMethod</i>
     *
     * @param d
     * @param isClass
     */
    public void addToRestObject(Declaration d, boolean isClass)
    {
        Collection<AnnotationMirror> annotations = d.getAnnotationMirrors();

        for (AnnotationMirror mirror : annotations)
        {
            String path = mirror.getPosition().file().getPath();

            RestObject object = toGenerate.get(path);

            if (isClass)
                addRestClassProperties(object, mirror);
            else
                addRestMethodProperties(object, mirror);

        }
    }

    private void addRestMethodProperties(RestObject object, AnnotationMirror mirror)
    {
        if (object != null)
        {

            Map<AnnotationTypeElementDeclaration, AnnotationValue> values = mirror.getElementValues();

            String path = null;
            String[] queryParams = null;

            for (Map.Entry<AnnotationTypeElementDeclaration, AnnotationValue> entry : values.entrySet())
            {
                AnnotationTypeElementDeclaration key = entry.getKey();
                AnnotationValue value = entry.getValue();

                if (key.getSimpleName().contains("path"))
                    path = (String) value.getValue();
                else if (key.getSimpleName().contains("queryParams"))
                {

                    List list = (List) value.getValue();

                    queryParams = new String[list.size()];
                    Iterator it = list.iterator();
                    for (int i = 0; it.hasNext(); i++)
                        queryParams[i] = ((AnnotationValue) it.next()).toString();
                }

            }

            if (path != null)
                object.addMethod(path, queryParams);
        }
    }

    private void addRestClassProperties(RestObject object, AnnotationMirror mirror)
    {
        String path = mirror.getPosition().file().getPath();
        if (object == null || !toGenerate.containsKey(path))
        {
            object = new RestObject();
            object.filePath = path;
            toGenerate.put(path, object);
        }

        Map<AnnotationTypeElementDeclaration, AnnotationValue> values = mirror.getElementValues();

        for (Map.Entry<AnnotationTypeElementDeclaration, AnnotationValue> entry : values.entrySet())
        {
            AnnotationTypeElementDeclaration key = entry.getKey();
            AnnotationValue value = entry.getValue();

            if (key.getSimpleName().contains("compiledName"))
                object.createPath = value.toString();
            else if (key.getSimpleName().contains("path"))
                object.servicePath = value.toString();

        }
    }

}
