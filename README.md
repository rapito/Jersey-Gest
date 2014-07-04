Jersey-Gest
===========

Java Annotation Extension for Jersey for creating REST/POST Methods

A simple utility which allows you to use a single java method to create 
a GET and a POST Operation Method for **Jersey**.

### Example Annotation Usage ###
<pre>
@RestClass(path = "/wsdl")
public class TestService
{

    @RestMethod(path = "/helloGest")
    public static String helloGest(String name)
    {
        return "<xml>Hello " + name + "</xml>";
    }

}
</pre>


### How it Works  ###

The class annotated with **@RestClass** is processed on compile time by apt-maven-plugin 
which then loads our custom AnnotationProcessor to generate a new Java Class. This
new class contains a **GET** and **POST** method that calls back to our method annotated with
**@RestMethod**.


### Why? ###
I was developing an application that needed to have a lot of services which had
to be accessed with both GET and POST Operations, but unfortunately Jersey doesn't 
support it. 

A workaround is creating not one but 3 methods for every Service 
Operation I wanted, using one as a Business container and the other two just for
declaring jersey's operation methods like this:

<pre>
    @GET
    @Path("/helloGest")
    @Produces(MediaType.APPLICATION_XML)
    public String helloGestGet()
    {
        return helloGest();
    }

    @POST
    @Path("/helloGest")
    @Consumes(MediaType.WILDCARD)
    @Produces(MediaType.APPLICATION_XML)
    public String helloGestPost()
    {
        return helloGest();
    }
    
    public static void helloGest()
    {
      return "Hello Gest!";
    }
</pre>
This was very troublesome so I decided to create my own annotation that would 
generate this for me.


### Dependencies ###

#### Core ####
* Java 5 SDK 
* Jersey 1.1.5 <https://github.com/jersey/jersey>
* JSR 311 

