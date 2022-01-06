package datacollection;

import filehandling.JsonFileHandler;
import request.MyHttpClient;
import request.MyResponse;

/**
 * Base Class for all Observers.
 * @param <T> the response class for http requests
 * @param <J> the class that wil be saved as JSON
 */
public abstract class AbstractObserver<T, J> implements Runnable{
  private final JsonFileHandler<J> fileHandler;
  private final JsonFileHandler<J> backupFileHandler; // Two FileHandlers, due to how Gson saves the json objects. This BackupFileHandler won't have a real JSON structure.
  private final MyHttpClient client;
  private final String uri;
  private final Class<T> type;
  
  /**
   * Constructor
   * @param uri the remaining path after /api/HUE_APPLICATION_KEY/ - e.g. lights/1
   * @param responseClass reflection for the response class of the http request
   * @param filename the name of the file to which the data will be saved
   */
  public AbstractObserver(String uri, Class<T> responseClass, String filename) {
    client = MyHttpClient.getInstance();
    this.uri = uri;
    this.type = responseClass;
    fileHandler = new JsonFileHandler<>(filename);
    backupFileHandler = new JsonFileHandler<>(filename + ".backup");
  }
  
  protected MyResponse<T> sendGet() {
    return client.sendGet(uri, type);
  }
  
  /**
   * Use together with {@code save} to get a proper JSON structure.
   * @param item the item to be added and saved later on
   */
  protected void addItemForSaving(J item) {
    fileHandler.addItem(item);
  }
  
  /**
   * Save all items that have been added via the {@code addItem} method.
   * This mehtod will create a proper JSON structure for multiple elements.
   */
  protected void saveAddedItems() {
    fileHandler.save();
  }
  
  /**
   * Save a single item.
   * It won't create a proper JSON structure for multiple items. It is missing the square brackets.
   * @param item the item to be saved
   */
  protected void saveItem(J item) {
    backupFileHandler.save(item, true);
  }
}
