package objects;

public class TemperatureSensor extends Sensor{

  public State state;

  public int isPresent() {
    return state.temperature;
  }

  class State {
    int temperature;
    String lastupdated;
  }
}
