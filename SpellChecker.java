import java.io.File;
import java.util.Scanner;

public class SpellChecker {
    // Use this field everytime you need to read user input
    private Scanner inputReader; // DO NOT MODIFY

    public SpellChecker() {
      inputReader = new Scanner(System.in); // DO NOT MODIFY - must be included in this method
      // TODO: Complete the body of this constructor, as necessary.
    }
  
    public void start() {
      // TODO: Complete the body of this method, as necessary.
      inputReader.close();  // DO NOT MODIFY - must be the last line of this method!
    }

    private boolean fileExists(String filename) {
        File f = new File(filename);
        return f.exists() && f.isFile();
    }


    // You can of course write other methods as well.
  }