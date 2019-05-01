package com.jersey;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;


// The Java class will be hosted at the URI path "/helloworld"
@Path("hello")
public class HelloWorld{
    // The Java method will process HTTP GET requests
    @GET
    // The Java method will produce content identified by the MIME Media type "text/plain"
    @Produces("text/plain")
    public Response getClichedMessage() {
        // Return some cliched textual content

        return Response.status(200).entity("hello").build();
    }
}