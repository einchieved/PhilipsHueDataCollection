package objects;

import config.Config;
import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Class representing the details for a motionsensor.
 * Located under /api/HUE_APPLICATION_KEY/sensors/<id>
 */
public class MotionSensor extends Sensor{
  public State state;

  public boolean isPresent() {
    return state.presence;
  }

  class State {
    boolean presence;
    String lastupdated;
  }
}


