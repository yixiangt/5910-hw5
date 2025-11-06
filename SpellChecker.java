import java.io.File;
import java.util.Scanner;

public class SpellChecker {
    // Use this field everytime you need to read user input
    private Scanner inputReader;// DO NOT MODIFY
    private WordRecommender recommender;

    public SpellChecker() {
      inputReader = new Scanner(System.in); // DO NOT MODIFY - must be included in this method
      // TODO: Complete the body of this constructor, as necessary.
    }
  
    public void start() {
      // TODO: Complete the body of this method, as necessary.
        String dictFile = getValidFilename(Util.DICTIONARY_PROMPT);
        recommender = new WordRecommender(dictFile);
        System.out.printf(Util.DICTIONARY_SUCCESS_NOTIFICATION, dictFile);

        inputReader.close();  // DO NOT MODIFY - must be the last line of this method!
    }

    private String getValidFilename(String prompt) {
        while (true) {
            System.out.printf(prompt);
            String filename = inputReader.nextLine().trim();
            if (fileExists(filename)) {
                return filename;
            } else {
                System.out.printf(Util.FILE_OPENING_ERROR);
            }
        }
    }
    private boolean fileExists(String filename) {
        File f = new File(filename);
        return f.exists() && f.isFile();
    }



    // You can of course write other methods as well.
  }