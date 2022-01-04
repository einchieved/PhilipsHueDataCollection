import objects.Light;
import request.MyHttpClient;
import request.MyResponse;

public class Main {
  
  public static void main(String[] args) {
    MyHttpClient client = MyHttpClient.getInstance();
    MyResponse<Light> response = client.sendGet("lights/1", Light.class);
  }
}
