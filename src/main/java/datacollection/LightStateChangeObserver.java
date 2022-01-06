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
  private final JsonFileHandler<StateChangeData> fileHandler;
  private final JsonFileHandler<StateChangeData> backupFileHandler; // Two FileHandlers, due to how Gson saves the json objects. This BackupFileHandler won't have a real JSON structure.
  private boolean isOn;
  
  /**
   * Creates an Observer for a single light.
   * @param id the id of the light (IDs start at 1)
   * @param filename the name of the file to which the data will be saved
   */
  public LightStateChangeObserver(int id, String filename) {
    this.lightUri = "lights/" + id;
    fileHandler = new JsonFileHandler<>(filename);
    backupFileHandler = new JsonFileHandler<>(filename + ".backup");
    client = MyHttpClient.getInstance();
    MyResponse<Light> response = client.sendGet(lightUri, Light.class);
    this.isOn = response.getResponseObject().isOn();
  }
  
  @Override
  public void run() {
    while (!Thread.currentThread().isInterrupted()) {
      try {
        Thread.sleep(1000);//60000
        MyResponse<Light> response = client.sendGet(lightUri, Light.class);
        if (isOn != response.getResponseObject().isOn()) {
          boolean newIsOn = response.getResponseObject().isOn();
          StateChangeData data = new StateChangeData(newIsOn? "on" : "off", response.getTime().orElse("time unknown"));
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
