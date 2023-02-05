package io.camunda.getstarted.tutorial;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;


import java.nio.charset.StandardCharsets;
import java.util.Base64;





public class PMServerCommunication {
    public int createTicket(String title) throws IOException {
        URL url = new URL("https://0bc864a1-f147-499e-94bd-d61d5cd44ade.mock.pstmn.io/issue");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setDoOutput(true);
        connection.setRequestMethod("POST");

        //String apiKey = "eyJ0dCI6InAiLCJhbGciOiJIUzI1NiIsInR2IjoiMSJ9.eyJkIjoie1wiYVwiOjU5NDgxMDQsXCJpXCI6ODUzMjUxNCxcImNcIjo0NjM3ODA4LFwidVwiOjE2MzI0MTI1LFwiclwiOlwiVVNcIixcInNcIjpbXCJXXCIsXCJGXCIsXCJJXCIsXCJVXCIsXCJLXCIsXCJDXCIsXCJEXCIsXCJNXCIsXCJBXCIsXCJMXCIsXCJQXCJdLFwielwiOltdLFwidFwiOjB9IiwiaWF0IjoxNjc1NTAwMzU3fQ.lnqiTXJnwjTU4yXeAAF_9gie-YF6LAbyPAmpvy7FlQI";
        //String encodedApiKey = encodeToBase64(apiKey);

        connection.setRequestProperty("Content-Type", "application/json");
        //connection.setRequestProperty("Authorization", "Bearer "+ apiKey);


        JSONObject payload = new JSONObject();
        JSONObject jsonResponse = null;
        payload.put("title", title);

        OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
        writer.write(payload.toString());
        writer.close();

        int responseCode = connection.getResponseCode();
        System.out.println("Response code: " + responseCode);

        StringBuilder response = new StringBuilder();

        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();
            jsonResponse = new JSONObject(response.toString());
            System.out.println("Response JSON: " + response.toString());
        } else {
            System.out.println("Request failed");
        }

        System.out.println(jsonResponse);

        int ticketId = jsonResponse.getInt("ticketId");
        System.out.println(ticketId);


        return ticketId;
    }

    public void deleteTicket() throws IOException {
        URL url = new URL("https://0bc864a1-f147-499e-94bd-d61d5cd44ade.mock.pstmn.io/issue/1");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setDoOutput(true);
        connection.setRequestMethod("DELETE");

        //String apiKey = "eyJ0dCI6InAiLCJhbGciOiJIUzI1NiIsInR2IjoiMSJ9.eyJkIjoie1wiYVwiOjU5NDgxMDQsXCJpXCI6ODUzMjUxNCxcImNcIjo0NjM3ODA4LFwidVwiOjE2MzI0MTI1LFwiclwiOlwiVVNcIixcInNcIjpbXCJXXCIsXCJGXCIsXCJJXCIsXCJVXCIsXCJLXCIsXCJDXCIsXCJEXCIsXCJNXCIsXCJBXCIsXCJMXCIsXCJQXCJdLFwielwiOltdLFwidFwiOjB9IiwiaWF0IjoxNjc1NTAwMzU3fQ.lnqiTXJnwjTU4yXeAAF_9gie-YF6LAbyPAmpvy7FlQI";
        //String encodedApiKey = encodeToBase64(apiKey);

        connection.setRequestProperty("Content-Type", "application/json");
        //connection.setRequestProperty("Authorization", "Bearer "+ apiKey);



        String payload = "{}";
        OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
        writer.write(payload);
        writer.close();

        int responseCode = connection.getResponseCode();
        System.out.println("Response code: " + responseCode);

        StringBuilder response = new StringBuilder();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();
            System.out.println("Response JSON: " + response.toString());
        } else {
            System.out.println("Request failed");
        }
    }


    public static String encodeToBase64(String data) {
        byte[] encodedBytes = Base64.getEncoder().encode(data.getBytes(StandardCharsets.UTF_8));
        return new String(encodedBytes, StandardCharsets.UTF_8);
    }

}



