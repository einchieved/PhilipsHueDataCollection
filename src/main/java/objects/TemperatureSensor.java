package objects;

public class TemperatureSensor extends Sensor{

  public State state;

  public String getTemperature() {
    return String.valueOf(state.temperature);
  }

  class State {
    int temperature;
    String lastupdated;
  }
}
