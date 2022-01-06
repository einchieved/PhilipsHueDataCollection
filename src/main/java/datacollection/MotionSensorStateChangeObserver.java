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

  public MotionSensorStateChangeObserver(String motionSensorUri) {
    this.motionSensorUri = motionSensorUri;
    fileHandler = new JsonFileHandler<>("MotionSensorStateChanged.json");
    backupFileHandler = new JsonFileHandler<>("MotionSensorStateChanged.backup.json");
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
