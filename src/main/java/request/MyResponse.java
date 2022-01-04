package request;

import com.google.gson.Gson;
import java.net.http.HttpResponse;

public class MyResponse<T> {
  private final HttpResponse<String> response;
  private final Class<T> typeParameterClass;
  private T obj;
  
  public MyResponse(HttpResponse<String> response, Class<T> clazz) {
    typeParameterClass = clazz;
    this.response = response;
  }
  
  public String getBody() {
    return response.body();
  }
  
  public int getStatusCode() {
    return response.statusCode();
  }
  
  public void printBody() {
    System.out.println(response.body());
  }
  
  public T getResponseObject() {
    if (obj == null) {
      Gson gson = new Gson();
      obj = gson.fromJson(response.body(), typeParameterClass);
    }
    return obj;
  }
}
