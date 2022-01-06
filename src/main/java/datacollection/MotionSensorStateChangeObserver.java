package datacollection;

import filehandling.JsonFileHandler;
import objects.MotionSensor;
import request.MyHttpClient;
import request.MyResponse;

/**
 * Class for periodically checking if the motion sensor recognized a person
 */
public class MotionSensorStateChangeObserver implements Runnable {

  private final String motionSensorUri;
  private final MyHttpClient client;
  private final JsonFileHandler<StateChangeData> fileHandler;
  private final JsonFileHandler<StateChangeData> backupFileHandler; // Two FileHandlers, due to how Gson saves the json objects. This BackupFileHandler won't have a real JSON structure.
  private boolean isPresent;
  
  /**
   * Creates an Observer for a single sensor.
   * @param id the id of the light (IDs start at 1)
   * @param filename the name of the file to which the data will be saved
   */
  public MotionSensorStateChangeObserver(int id, String filename) {
    this.motionSensorUri = "sensors/" + id;
    fileHandler = new JsonFileHandler<>(filename);
    backupFileHandler = new JsonFileHandler<>(filename + ".backup");
    client = MyHttpClient.getInstance();
    MyResponse<MotionSensor> response = client.sendGet(motionSensorUri, MotionSensor.class);
    this.isPresent = response.getResponseObject().isPresent();
  }

  @Override
  public void run() {
    while (!Thread.currentThread().isInterrupted()) {
      try {
        Thread.sleep(1000);//60000
        MyResponse<MotionSensor> response = client.sendGet(motionSensorUri, MotionSensor.class);
        if (isPresent != response.getResponseObject().isPresent()) {
          boolean newIsPresent = response.getResponseObject().isPresent();
          StateChangeData data = new StateChangeData(newIsPresent? "present" : "absent", response.getTime().orElse("time unknown"));
          fileHandler.addItem(data);
          backupFileHandler.save(data, true);
          this.isPresent = newIsPresent;
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
