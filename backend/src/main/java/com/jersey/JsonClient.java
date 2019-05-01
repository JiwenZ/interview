package com.jersey;

import com.jersey.Data;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import net.sf.json.JSONObject;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class JsonClient {
    public static void main(String[] args) {
        try
        {
            JSONObject json = new JSONObject();
            Random rand = new Random();
            List<Integer> list = new ArrayList<Integer>();
            for(int i = 0; i < 500; i ++){
                list.add(rand.nextInt(1000));
            }
            json.accumulate("rand", list);

            Client client = Client.create();

            WebResource resource = client
                    .resource("http://localhost:8080/Rest_glassfish_hello_world_war/check");

            ClientResponse response = resource
                    .type("application/json").post(ClientResponse.class, json.toString());

            if (response.getStatus() != 200) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + response.getStatus());
            }

            String data = response.getEntity(String.class);
            System.out.println("Data: "+data);

        } catch (Exception e)
        {
            e.printStackTrace();
        }

//        try {
//            URL url = new URL("http://localhost:8080/Rest_glassfish_hello_world_war_exploded/data");
//            URLConnection connection = url.openConnection();
//            connection.setDoOutput(true);
//            connection.setRequestProperty("Content-Type", "application/json");
//            connection.setConnectTimeout(5000);
//            connection.setReadTimeout(5000);
//            OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());
//            out.write(json.toString());
//            out.close();
//
//            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
//
//            while (in.readLine() != null) {
//            }
//            System.out.println("\nCrunchify REST Service Invoked Successfully..");
//            in.close();
//        } catch (Exception e) {
//            System.out.println("\nError while calling Crunchify REST Service");
//            System.out.println(e);
//        }
    }
}
