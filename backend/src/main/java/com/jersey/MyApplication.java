package com.jersey;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashSet;
import java.util.Set;


//Defines the base URI for all resource URIs.
@ApplicationPath("/")
//The java class declares root resource and provider classes
public class MyApplication extends Application{
    //    The method returns a non-empty collection with classes, that must be included in the published JAX-RS application
//    @Override
//    public Set<Class<?>> getClasses() {
//        HashSet h = new HashSet<Class<?>>();
//        h.add(FormatCheck.class);
//        return h;
//    }

}