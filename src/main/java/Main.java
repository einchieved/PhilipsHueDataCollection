import datacollection.LightStateChangeObserver;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Main {
  
  public static void main(String[] args) {
    List<Thread> threads = new LinkedList<>();
    
    // Create and assign Observers to list
    threads.add(new Thread(new LightStateChangeObserver("lights/1")));
    
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
