package io.camunda.getstarted.tutorial;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

// API Key : ATCTT3xFfGN0yJSZS1gHQ_ZZCaFLvCJh-axBq2ezt7PYEJjUFZ84SgcSMS71pjTQ85xCdx7E_g8RgEUutjE47OY7sHskDq-RmlzCxRGdfutrULpaWhCZOsyIsU50AK0BrnbkUL9NuVscI7lbF1-CAbbI9dBO9mAcobF5c3-Ihd1AXc3z6qVsg48=ED2382D2

// Orginsation Key : a2320a46-44aa-1099-6daj-j9ad2ab5649a


public class JiraCreateTicket {
    public static void main(String[] args) throws Exception {
        HttpClient httpClient = createHttpClient();

        HttpPost request = new HttpPost("https://hsrtbpt2022.atlassian.net/rest/api/2/issue");

        JSONObject requestBody = new JSONObject();
        requestBody.put("fields", new JSONObject()
                .put("project", new JSONObject().put("key", "FRC"))
                .put("summary", "This is a Test Ticket")
                .put("description", "This describes a test - Ticket")
                .put("issuetype", new JSONObject().put("name", "Task"))
        );
        StringEntity entity = new StringEntity(requestBody.toString());
        request.setEntity(entity);
        request.setHeader("Accept", "application/json");
        request.setHeader("Content-type", "application/json");

        HttpResponse response = httpClient.execute(request);
        HttpEntity responseEntity = response.getEntity();

        String responseString = EntityUtils.toString(responseEntity, "UTF-8");
        JSONObject responseBody = new JSONObject(responseString);

        String issueKey = responseBody.getString("key");
        String issueId = responseBody.getString("id");

        System.out.println("Issue created with key: " + issueKey + " and id: " + issueId);
    }

    private static HttpClient createHttpClient() {

        String username = "Michael Strasser";
        String api_token = "ATCTT3xFfGN0yJSZS1gHQ_ZZCaFLvCJh-axBq2ezt7PYEJjUFZ84SgcSMS71pjTQ85xCdx7E_g8RgEUutjE47OY7sHskDq-RmlzCxRGdfutrULpaWhCZOsyIsU50AK0BrnbkUL9NuVscI7lbF1-CAbbI9dBO9mAcobF5c3-Ihd1AXc3z6qVsg48=ED2382D2";
        CredentialsProvider provider = new BasicCredentialsProvider();
        UsernamePasswordCredentials credentials = new UsernamePasswordCredentials(username, api_token);
        provider.setCredentials(AuthScope.ANY, credentials);
        return HttpClientBuilder.create().setDefaultCredentialsProvider(provider).build();
    }
}
