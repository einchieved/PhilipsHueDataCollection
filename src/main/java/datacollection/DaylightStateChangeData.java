package datacollection;

/**
 * Class holding information about state changes of a daylight sensor.
 * Used for saving information in a JSON file.
 */
public class DaylightStateChangeData {
  private final int lightlevel;
  private final boolean dark;
  private final boolean daylight;
  private final String time;


  public DaylightStateChangeData(int lightlevel, boolean dark, boolean daylight, String time) {
    this.lightlevel = lightlevel;
    this.dark = dark;
    this.daylight = daylight;
    this.time = time;
  }

  public int getLightlevel() {
    return lightlevel;
  }

  public boolean isDark() {
    return dark;
  }

  public boolean isDaylight() {
    return daylight;
  }

  public String getTime() {
    return time;
  }
}
