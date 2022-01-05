package datacollection;

import filehandling.JsonFileHandler;
import objects.Light;
import request.MyHttpClient;
import request.MyResponse;

/**
 * Class for periodically checking if a light has been turned on or off.
 */
public class LightStateChangeObserver implements Runnable {
  private final String lightUri;
  private final MyHttpClient client;
  private final JsonFileHandler<LightStateChangeData> fileHandler;
  private final JsonFileHandler<LightStateChangeData> backupFileHandler; // Two FileHandlers, due to how Gson saves the json objects. This BackupFileHandler won't have a real JSON structure.
  private boolean isOn;
  
  public LightStateChangeObserver(String lightUri) {
    this.lightUri = lightUri;
    fileHandler = new JsonFileHandler<>("lightStateChanged.json");
    backupFileHandler = new JsonFileHandler<>("lightStateChanged.backup.json");
    client = MyHttpClient.getInstance();
    MyResponse<Light> response = client.sendGet(lightUri, Light.class);
    this.isOn = response.getResponseObject().isOn();
  }
  
  public LightStateChangeObserver(String lightUri, boolean isOn) {
    this.lightUri = lightUri;
    backupFileHandler = new JsonFileHandler<>("lightStateChanged.backup.json");
    fileHandler = new JsonFileHandler<>("lightStateChanged.json");
    client = MyHttpClient.getInstance();
    this.isOn = isOn;
  }
  
  @Override
  public void run() {
    while (!Thread.currentThread().isInterrupted()) {
      try {
        Thread.sleep(1000);//60000
        MyResponse<Light> response = client.sendGet(lightUri, Light.class);
        if (isOn != response.getResponseObject().isOn()) {
          boolean newIsOn = response.getResponseObject().isOn();
          LightStateChangeData data = new LightStateChangeData(newIsOn? "on" : "off", response.getTime().orElse("time unknown"));
          fileHandler.addItem(data);
          backupFileHandler.save(data, true);
          this.isOn = newIsOn;
        }
      } catch (InterruptedException ignored) {
        // used to stop the thread
        fileHandler.save();
        return;
      }
    }
    fileHandler.save();
  }
}
