package request;

import com.google.gson.Gson;
import java.net.http.HttpResponse;
import java.util.Optional;

/**
 * Class that contains the response of a request.
 * @param <T> the type of the response - e.g. {@code Light} if path was /lights/1
 */
public class MyResponse<T> {
  private final String uri;
  private final HttpResponse<String> response;
  private final Class<T> typeParameterClass;
  private T obj;
  
  public MyResponse(HttpResponse<String> response, String uri, Class<T> clazz) {
    typeParameterClass = clazz;
    this.uri = uri;
    this.response = response;
  }
  
  public String getBody() {
    return response.body();
  }
  
  public int getStatusCode() {
    return response.statusCode();
  }
  
  public Optional<String> getTime() {
    return response.headers().firstValue("Date");
  }
  
  /**
   * Print the response body to the console.
   */
  public void printBody() {
    System.out.println(response.body());
  }
  
  /**
   * Returns the object that this response represents.
   * @return the object representeed by this repsonse
   */
  public T getResponseObject() {
    if (obj == null) {
      Gson gson = new Gson();
      obj = gson.fromJson(response.body(), typeParameterClass);
    }
    return obj;
  }
  
  public String getUri() {
    return uri;
  }
}
