package objects;

/**
 * Class representing error messages.
 */
public class Error {
  public ErrorDetails error;
  
  public class ErrorDetails {
    public int type;
    public String address, description;
  }
}
