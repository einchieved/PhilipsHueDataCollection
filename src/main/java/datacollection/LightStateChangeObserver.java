package datacollection;

import objects.Light;
import request.MyResponse;

/**
 * Class for periodically checking if a light has been turned on or off.
 */
public class LightStateChangeObserver extends AbstractObserver<Light, StateChangeData> {
  private boolean isOn;
  
  /**
   * Creates an Observer for a single light.
   * @param id the id of the light (IDs start at 1)
   * @param filename the name of the file to which the data will be saved
   */
  public LightStateChangeObserver(int id, String filename) {
    super("lights/" + id, Light.class, filename);
    MyResponse<Light> response = sendGet();
    this.isOn = response.getResponseObject().isOn();
  }
  
  @Override
  public void run() {
    while (!Thread.currentThread().isInterrupted()) {
      try {
        Thread.sleep(1000);//60000
        MyResponse<Light> response = sendGet();
        if (isOn != response.getResponseObject().isOn()) {
          boolean newIsOn = response.getResponseObject().isOn();
          StateChangeData data = new StateChangeData(newIsOn? "on" : "off", response.getTime().orElse("time unknown"));
          addItemForSaving(data);
          saveItem(data);
          this.isOn = newIsOn;
        }
      } catch (InterruptedException ignored) {
        // used to stop the thread
        saveAddedItems();
        return;
      }
    }
    saveAddedItems();
  }
}
