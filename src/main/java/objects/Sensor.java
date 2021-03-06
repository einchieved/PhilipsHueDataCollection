package objects;

import config.Config;
import java.util.ArrayList;

/**
 * Class representing the details for a sensor.
 * Located under /api/HUE_APPLICATION_KEY/sensors/<id>
 */
public class Sensor {
  public Update swupdate;
  public Config config;
  public String name;
  public String type;
  public String modelId;
  public String manufacturername;
  public String productname;
  public String swversion;
  public String uniqueid;
  Capabilities capabilities;

  class Update {
    String state, lastinstall;
  }

  class Capabilities {
    boolean certiefied;
    boolean primary;
  }

  class Confiq {
    boolean on;
    int battery;
    boolean reachable;
    String alert;
    int sensitivity;
    int sensitivitymax;
    boolean ledindication;
    boolean usertest;
    ArrayList pending;
  }
}

