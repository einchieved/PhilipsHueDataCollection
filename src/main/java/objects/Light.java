package objects;

/**
 * Class representing the details for a lamp.
 * Located under /api/HUE_APPLICATION_KEY/lights/<id>
 */
public class Light {
  public State state;
  public Update swupdate;
  public String type, name, modelid, manufacturername, productname, uniqueid, swversion;
  public Capabilities capabilities;
  public LightConfig config;
  
  public boolean isOn() {
    return state.on;
  }
  
  public class State {
    boolean on, reachable;
    int bri, hue, sat, ct;
    double[] xy;
    String effect, alert, colormode, mode;
  }
  
  public class Update {
    String state, lastinstall;
  }
  
  public class Capabilities {
    boolean certified;
    Control control;
    Streaming streaming;
  }
  
  public class Control {
    double mindimlevel, maxlumen;
    String colorgamuttype;
    double [][] colorgamut;
    CT ct;
  }
  
  public class CT {
    double min, max;
  }
  
  public class Streaming {
    boolean renderer, proxy;
  }
  
  public class LightConfig {
    String archetype, function, direction;
  }
}
