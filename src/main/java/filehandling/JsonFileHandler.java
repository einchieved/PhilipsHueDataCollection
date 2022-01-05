package filehandling;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import config.Config;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.LinkedList;
import java.util.List;

/**
 * Class responsible for saving data in JSON format.
 * @param <T> the type of object to be save
 */
public class JsonFileHandler<T> {
  private final String LOCATION = System.getProperty("user.dir") + (Config.IS_OS_WINDOWS ? "\\files\\" : "/files/");;
  private final String filename;
  private final List<T> itemList;
  private final Gson gson;
  
  public JsonFileHandler(String filename) {
    itemList = new LinkedList<>();
    this.filename = checkFilename(filename);
    gson = new GsonBuilder().setPrettyPrinting().create();
  }
  
  /**
   * Use together with {@code save} to get a proper JSON structure.
   * @param item the item to be added and saved later on
   */
  public void addItem(T item) {
    itemList.add(item);
  }
  
  /**
   * Save all items that have been added via the {@code addItem} method.
   * This mehtod will create a proper JSON structure for multiple elements.
   */
  public void save() {
    try {
      Writer writer = new FileWriter(LOCATION + filename);
      gson.toJson(itemList, writer);
      writer.flush();
      writer.close();
    } catch (IOException e) {
      System.out.println("Could not save file!");
    }
  }
  
  /**
   * Save a single item.
   * It won't create a proper JSON structure for multiple items. It is missing the square brackets.
   * @param item the item to be saved
   * @param append if true, then data will be written to the end of the file rather than the beginning
   */
  public void save(T item, boolean append) {
    try {
      Writer writer = new FileWriter(LOCATION + filename, append);
      gson.toJson(item, writer);
      writer.flush();
      writer.close();
    } catch (IOException e) {
      System.out.println("Could not save file!");
    }
  }
  
  private String checkFilename(String filename) {
    if (!filename.endsWith(".json")) {
      return filename + ".json";
    }
    return filename;
  }
}
