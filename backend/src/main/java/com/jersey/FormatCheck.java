package com.jersey;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Path("/")
public class FormatCheck {
    @GET
    @Path("/data")
    @Produces("application/json")
    public String produceJSON() {

        JSONObject json = new JSONObject();
        Random rand = new Random();
        List<Integer> list = new ArrayList<Integer>();
        for (int i = 0; i < 500; i++) {
            list.add(rand.nextInt(1000));
        }
        json.accumulate("rand", list);
        return json.toString();
    }
    @POST
    @Path("/check")
    @Consumes("application/json")
    public Response consumeJSON(String data) {
//        String result = "Saved Customer : " + data;
//        System.out.println(data.getRandNum());
        return Response.status(200).entity(data).build();
    }
}

//    @GET
//    @Path("/verify")
//    @Produces(MediaType.APPLICATION_JSON)
//    public Response verifyRESTService(org.json.JSONArray incomingData) {
//        String result = "CrunchifyRESTService Successfully started..";
//
//        // return HTTP response 200 in case of success
//        return Response.status(200).entity(result).build();
//    }

