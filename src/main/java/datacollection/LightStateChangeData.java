package datacollection;

/**
 * Class holding information about state changes of a light.
 * Used for saving information in a JSON file.
 */
public class LightStateChangeData {
  private final String newState;
  private final String time;
  
  public LightStateChangeData(String newState, String time) {
    this.newState = newState;
    this.time = time;
  }
  
  public String getNewState() {
    return newState;
  }
  
  public String getTime() {
    return time;
  }
}
