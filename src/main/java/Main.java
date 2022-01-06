import datacollection.DayLightChangeObserver;
import datacollection.LightStateChangeObserver;
import datacollection.MotionStateChangeObserver;
import datacollection.TemperatureChangeObserver;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Main {
  
  public static void main(String[] args) {
    List<Thread> threads = new LinkedList<>();
    
    // Create and assign Observers to list
    threads.add(new Thread(new LightStateChangeObserver(1, "LightStateChanged")));
    threads.add(new Thread(new MotionStateChangeObserver(2, "MotionStateChanged")));
    threads.add(new Thread(new DayLightChangeObserver(3, "DayLightChanged")));
    threads.add(new Thread(new TemperatureChangeObserver(4, "TemperatureChanged")));


    // Start Observers
    for (Thread thread : threads) {
      thread.start();
    }
  
    System.out.println("Enter anything to exit:");
    new Scanner(System.in).nextLine();
    System.out.println("Exiting ...");
    
    // Stop Observers
    for (Thread thread : threads) {
      thread.interrupt();
    }
  
    System.out.println("Finished!");
  }
}
