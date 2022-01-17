package request;

import com.google.gson.Gson;
import java.net.http.HttpResponse;

/**
 * Class that contains the response of a request.
 * @param <T> the type of the response - e.g. {@code Light} if path was /lights/1
 */
public class MyResponse<T> {
  private final HttpResponse<String> response;
  private final Class<T> typeParameterClass;
  private T obj;
  
  public MyResponse(HttpResponse<String> response, Class<T> clazz) {
    typeParameterClass = clazz;
    this.response = response;
  }

  /**
   * Returns the object that this response represents.
   * @return the object represented by this response
   */
  public T getResponseObject() {
    if (obj == null) {
      Gson gson = new Gson();
      obj = gson.fromJson(response.body(), typeParameterClass);
    }
    return obj;
  }

}
