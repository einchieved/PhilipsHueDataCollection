package objects;

public class Light {
  public State state;
  public Update swupdate;
  public String type,
  name,
  modelid,
  manufacturername,
  productname;
  public Capabilities capabilities;
  public LightConfig config;
  public String uniqueid,
  swversion;
  
  public class State {
    boolean on;
    int bri,
    hue,
    sat;
    String effect;
    double[] xy;
    int ct;
    String alert,
    colormode,
    mode;
    boolean reachable;
  }
  
  public class Update {
    String state,
    lastinstall;
  }
  
  public class Capabilities {
    boolean certified;
    Control control;
    Streaming streaming;
    
    public class Control {
      int mindimlevel,
      maxlumen;
      String colorgamuttype;
      int [][] colorgamut;
      CT ct;
      
      public class CT {
        int min,
        max;
      }
    }
    
    public class Streaming {
      boolean renderer,
      proxy;
    }
  }
  
  public class LightConfig {
    String archetype,
    function,
    direction;
  }
}
