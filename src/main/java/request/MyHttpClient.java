package request;

import config.Config;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

/**
 * Class for making HTTP-Requests.
 */
public class MyHttpClient {
  private final HttpClient httpClient;
  private static MyHttpClient me;
  
  private MyHttpClient() {
    httpClient = HttpClient.newHttpClient();
  }
  
  public static MyHttpClient getInstance() {
    if (me == null) {
      me = new MyHttpClient();
    }
    return me;
  }
  
  /**
   * Makes a Get-Request to the specified URI and returns the result.
   * @param path the remaining path after /api/HUE_APPLICATION_KEY
   * @param responseType the expected class - e.g. {@code Light} if path is /lights/1
   * @return the response
   */
  public <T> MyResponse<T> sendGet(String path, Class<T> responseType) {
    HttpRequest request = createBaseRequest(path).GET().build();
    try {
      HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
      return new MyResponse<>(response, responseType);
    } catch (IOException | InterruptedException e) {
      e.printStackTrace();
    }
    return null;
  }
  
  /**
   * Builds the base part of the request that is always the same and the URI.
   * @param path the remaining path after /api/HUE_APPLICATION_KEY
   * @return the builder for the request
   */
  private HttpRequest.Builder createBaseRequest(String path) {
    return HttpRequest.newBuilder()
                      .uri(createUri(path))
                      .header("Content-Type", "application/json");
  }
  
  /**
   * Builds a URI
   * @param path the remaining path after /api/HUE_APPLICATION_KEY
   * @return the URI
   */
  private URI createUri(String path) {
    String basePath = "http://" + Config.ADDRESS + "/api/" + Config.HUE_APPLICATION_KEY;
    if (path.startsWith("/")) {
      return URI.create(basePath + path);
    }
    return URI.create(basePath + "/" + path);
  }
}
