package datacollection;

import objects.DayLightSensor;
import request.MyResponse;

/**
 * Class for periodically checking if the lightlevel of a daylight sensor has changed.
 */
public class DayLightChangeObserver extends AbstractObserver<DayLightSensor, DaylightStateChangeData> {
  private int lightlevel;
  private boolean dark;
  private boolean daylight;

  /**
   * Creates an Observer for a single daylight sensor.
   * @param id the id of the sensor (IDs start at 1)
   * @param filename the name of the file to which the data will be saved
   */
  public DayLightChangeObserver(int id, String filename) {
    super("sensors/" + id, DayLightSensor.class, filename);
    MyResponse<DayLightSensor> response = sendGet();
    this.lightlevel = response.getResponseObject().getlightLevel();
    this.dark = response.getResponseObject().isDark();
    this.daylight = response.getResponseObject().isDayLight();
  }

  @Override
  public void run() {
    while (!Thread.currentThread().isInterrupted()) {
      try {
        Thread.sleep(1000);//60000
        MyResponse<DayLightSensor> response = sendGet();
        if (lightlevel != response.getResponseObject().getlightLevel()) {
          int newLightLevel = response.getResponseObject().getlightLevel();
          boolean newDark = response.getResponseObject().isDark();
          boolean newDayLight = response.getResponseObject().isDayLight();
          DaylightStateChangeData data = new DaylightStateChangeData(newLightLevel, newDark, newDayLight, response.getTime().orElse("time unknown"));

          addItemForSaving(data);
          saveItem(data);
          this.lightlevel = newLightLevel;
          this.dark = newDark;
          this.daylight = newDayLight;
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

