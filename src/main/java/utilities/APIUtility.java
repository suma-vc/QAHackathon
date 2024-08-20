package utilities;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * The APIUtility class provides utility methods for making API requests.
 * These methods include GET, POST, PUT, and DELETE requests.
 */

public class APIUtility {
	private static final Logger logger = LoggerFactory.getLogger(FileUtility.class);



    /**
     * Performs a GET request to the specified URL.
     *
     * @param url The URL to send the GET request to.
     * @return The response from the server as a String.
     * @throws IOException If an input or output exception occurred.
     */
    public static String performGetRequest(String url) throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet request = new HttpGet(url);
        HttpResponse response = httpClient.execute(request);
        HttpEntity entity = response.getEntity();
        return EntityUtils.toString(entity);
    }

    /**
     * Performs a POST request to the specified URL with the provided JSON body.
     *
     * @param url The URL to send the POST request to.
     * @param jsonBody The JSON body to include in the POST request.
     * @return The response from the server as a String.
     * @throws IOException If an input or output exception occurred.
     */
    public static String performPostRequest(String url, String jsonBody) throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost request = new HttpPost(url);
        StringEntity entity = new StringEntity(jsonBody);
        request.setEntity(entity);
        request.setHeader("Accept", "application/json");
        request.setHeader("Content-type", "application/json");
        HttpResponse response = httpClient.execute(request);
        return EntityUtils.toString(response.getEntity());
    }

    /**
     * Performs a PUT request to the specified URL with the provided JSON body.
     *
     * @param url The URL to send the PUT request to.
     * @param jsonBody The JSON body to include in the PUT request.
     * @return The response from the server as a String.
     * @throws IOException If an input or output exception occurred.
     */
    public static String performPutRequest(String url, String jsonBody) throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPut request = new HttpPut(url);
        StringEntity entity = new StringEntity(jsonBody);
        request.setEntity(entity);
        request.setHeader("Accept", "application/json");
        request.setHeader("Content-type", "application/json");
        HttpResponse response = httpClient.execute(request);
        return EntityUtils.toString(response.getEntity());
    }

    /**
     * Performs a DELETE request to the specified URL.
     *
     * @param url The URL to send the DELETE request to.
     * @return The response from the server as a String.
     * @throws IOException If an input or output exception occurred.
     */
    public static String performDeleteRequest(String url) throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpDelete request = new HttpDelete(url);
        HttpResponse response = httpClient.execute(request);
        return EntityUtils.toString(response.getEntity());
    }
    

}
