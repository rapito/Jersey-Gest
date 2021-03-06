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

package my.services;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * This class has been generated by RestAnnotationProcessor by Robert Peralta 
 * to create a basic Jersey service for POST/GET operations.
 */
@Path("/wsdl")
public class TestServiceImpl
{


	@GET
	@Path("/helloGest")
	@Produces(MediaType.APPLICATION_XML)
	public String helloGestGet(@QueryParam("name") String name)
	{
		return com.fingled.jersey.services.TestService.helloGest(name);
	}


	@POST
	@Path("/helloGest")
	@Consumes(MediaType.WILDCARD)
	@Produces(MediaType.APPLICATION_XML)
	public String helloGestPost(@FormParam("name") String name)
	{
		return com.fingled.jersey.services.TestService.helloGest(name);
	}


	@GET
	@Path("/byeGest")
	@Produces(MediaType.APPLICATION_XML)
	public String byeGestGet(@QueryParam("msg") String msg, @QueryParam("name") String name)
	{
		return com.fingled.jersey.services.TestService.byeGest(msg, name);
	}


	@POST
	@Path("/byeGest")
	@Consumes(MediaType.WILDCARD)
	@Produces(MediaType.APPLICATION_XML)
	public String byeGestPost(@FormParam("msg") String msg, @FormParam("name") String name)
	{
		return com.fingled.jersey.services.TestService.byeGest(msg, name);
	}


	@GET
	@Path("/welcomeGest")
	@Produces(MediaType.APPLICATION_XML)
	public String welcomeGestGet()
	{
		return com.fingled.jersey.services.TestService.welcomeGest();
	}


	@POST
	@Path("/welcomeGest")
	@Consumes(MediaType.WILDCARD)
	@Produces(MediaType.APPLICATION_XML)
	public String welcomeGestPost()
	{
		return com.fingled.jersey.services.TestService.welcomeGest();
	}




}
