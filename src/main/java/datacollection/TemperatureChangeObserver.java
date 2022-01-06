package datacollection;

import java.time.LocalDateTime;
import objects.TemperatureSensor;
import request.MyResponse;

/**
 * Class for periodically checking if the motion sensor recognized a person
 */
public class TemperatureChangeObserver extends AbstractObserver<TemperatureSensor, StateChangeData> {
  private String temperature;

  /**
   * Creates an Observer for a single sensor.
   * @param id the id of the light (IDs start at 1)
   * @param filename the name of the file to which the data will be saved
   */
  public TemperatureChangeObserver(int id, String filename) {
    super("sensors/" + id, TemperatureSensor.class, filename);
    MyResponse<TemperatureSensor> response = sendGet();
    this.temperature = response.getResponseObject().getTemperature();
  }

  @Override
  public void run() {
    while (!Thread.currentThread().isInterrupted()) {
      try {
        Thread.sleep(1000);//60000
        MyResponse<TemperatureSensor> response = sendGet();
        if (!temperature.equals(response.getResponseObject().getTemperature())) {
          String newTemperature = response.getResponseObject().getTemperature();
          StateChangeData data = new StateChangeData(newTemperature, LocalDateTime.now().format(dateTimeFormatter));
          addItemForSaving(data);
          saveItem(data);
          this.temperature = newTemperature;
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
