package datacollection;

import java.time.LocalDateTime;
import objects.MotionSensor;
import request.MyResponse;

/**
 * Class for periodically checking if the motion sensor recognized a person.
 */
public class MotionStateChangeObserver extends AbstractObserver<MotionSensor, StateChangeData> {
  private boolean isPresent;
  
  /**
   * Creates an Observer for a single motion sensor.
   * @param id the id of the sensor (IDs start at 1)
   * @param filename the name of the file to which the data will be saved
   */
  public MotionStateChangeObserver(int id, String filename) {
    super("sensors/" + id, MotionSensor.class, filename);
    MyResponse<MotionSensor> response = sendGet();
    this.isPresent = response.getResponseObject().isPresent();
  }

  @Override
  public void run() {
    while (!Thread.currentThread().isInterrupted()) {
      try {
        Thread.sleep(1000);//60000
        MyResponse<MotionSensor> response = sendGet();
        if (isPresent != response.getResponseObject().isPresent()) {
          boolean newIsPresent = response.getResponseObject().isPresent();
          StateChangeData data = new StateChangeData(newIsPresent? "present" : "absent", LocalDateTime.now().format(dateTimeFormatter));
          addItemForSaving(data);
          saveItem(data);
          this.isPresent = newIsPresent;
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
