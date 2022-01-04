package request;

import config.Config;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

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
  
  public <T> MyResponse<T> sendGet(String path, Class<T> responseType) {
    HttpRequest request = createBaseRequest(path).GET().build();
    System.out.println("URI: " + request.uri());
    try {
      HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
      return new MyResponse<>(response, responseType);
    } catch (IOException | InterruptedException e) {
      e.printStackTrace();
    }
    return null;
  }
  
  private HttpRequest.Builder createBaseRequest(String path) {
    return HttpRequest.newBuilder()
                      .uri(createUri(path))
                      .header("Content-Type", "application/json");
  }
  
  /**
   * Builds a URI
   * @param path the remaining path after clip/v2/resource
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
