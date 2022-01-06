package objects;

public class DayLightSensor extends Sensor{

  public State state;

  public int getlightLevel() {
    return state.lightlevel;
  }

  public boolean isDark() {
    return state.dark;
  }

  public boolean isDayLight() {
    return state.daylight;
  }

  class State {
    int lightlevel;
    boolean dark;
    boolean daylight;
    String lastupdated;
  }
}
