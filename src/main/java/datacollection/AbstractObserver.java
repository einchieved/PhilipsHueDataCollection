package datacollection;

import filehandling.JsonFileHandler;
import request.MyHttpClient;
import request.MyResponse;

public abstract class AbstractObserver<T, J> implements Runnable{
  private final JsonFileHandler<J> fileHandler;
  private final JsonFileHandler<J> backupFileHandler; // Two FileHandlers, due to how Gson saves the json objects. This BackupFileHandler won't have a real JSON structure.
  private final MyHttpClient client;
  private final String uri;
  private final Class<T> type;
  
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
  
  protected void addItemForSaving(J item) {
    fileHandler.addItem(item);
  }
  
  protected void saveAddedItems() {
    fileHandler.save();
  }
  
  protected void saveItem(J item) {
    backupFileHandler.save(item, true);
  }
}
