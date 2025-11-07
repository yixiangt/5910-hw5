import java.io.*;
import java.util.Scanner;
import java.util.ArrayList;

public class SpellChecker {
    // Use this field everytime you need to read user input
    private Scanner inputReader;// DO NOT MODIFY
    private WordRecommender recommender;

    public SpellChecker() {
        inputReader = new Scanner(System.in); // DO NOT MODIFY - must be included in this method
    }
  
    public void start() {
        String dictFilename = getValidFilename(Util.DICTIONARY_PROMPT);
        recommender = new WordRecommender(dictFilename);
        System.out.printf(Util.DICTIONARY_SUCCESS_NOTIFICATION, dictFilename);
        String inputFilename = getValidFilename(Util.FILENAME_PROMPT);
        String outputFilename = inputFilename.replace(".txt", "_chk.txt");
        if (!outputFilename.endsWith("_chk.txt")) {
            outputFilename = inputFilename + "_chk.txt";
        }
        System.out.printf(Util.FILE_SUCCESS_NOTIFICATION, inputFilename, outputFilename);

        ArrayList<String> words = getWords(inputFilename);
        writeCheckedFile(words, outputFilename);

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

    private ArrayList<String> getWords(String filename) {
        ArrayList<String> words = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;
                String[] parts = line.split("\\s+");
                for (String p : parts) {
                    if (!p.isEmpty()){
                        words.add(p);
                    }
                }
            }
        } catch (IOException ignored) {
        }
        return words;
    }

    private String handleMisspelled(String word) {
        System.out.printf(Util.MISSPELL_NOTIFICATION, word);
        ArrayList<String> suggestions = recommender.getWordSuggestions(word, 2, 0.5, 4);
        String userChoice;
        String replacement;

        if (suggestions.isEmpty()) {
            System.out.printf(Util.NO_SUGGESTIONS);
            System.out.printf(Util.TWO_OPTION_PROMPT);
            while (true) {
                userChoice = inputReader.nextLine().trim().toLowerCase();
                if (userChoice.equals("a")) {
                    return word;
                } else if (userChoice.equals("t")) {
                    System.out.printf(Util.MANUAL_REPLACEMENT_PROMPT);
                    replacement = inputReader.nextLine().trim();
                    return replacement;
                } else {
                    System.out.printf(Util.INVALID_RESPONSE);
                }
            }
        } else {
            System.out.printf(Util.FOLLOWING_SUGGESTIONS);
            for (int i = 0; i < suggestions.size(); i++) {
                System.out.printf(Util.SUGGESTION_ENTRY, i + 1, suggestions.get(i));
            }
            System.out.printf(Util.THREE_OPTION_PROMPT);
            while (true) {
                userChoice = inputReader.nextLine().trim().toLowerCase();
                if (userChoice.equals("r")) {
                    while (true) {
                        System.out.printf(Util.AUTOMATIC_REPLACEMENT_PROMPT);
                        String input = inputReader.nextLine().trim();
                        try {
                            int index = Integer.parseInt(input);
                            if (index >= 1 && index <= suggestions.size()) {
                                return suggestions.get(index - 1);
                            }
                        } catch (NumberFormatException ignored) {
                        }
                        System.out.printf(Util.INVALID_RESPONSE);
                    }
                } else if (userChoice.equals("a")) {
                    return word;
                } else if (userChoice.equals("t")) {
                    System.out.printf(Util.MANUAL_REPLACEMENT_PROMPT);
                    replacement = inputReader.nextLine().trim();
                    return replacement;
                } else {
                    System.out.printf(Util.INVALID_RESPONSE);
                }
            }
        }
    }
    private void writeCheckedFile(ArrayList<String> words, String outputFile) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(outputFile))) {
            boolean first = true;
            for (String w : words) {
                String newOut;
                if (recommender.isInDictionary(w)) {
                    newOut = w;
                } else {
                    newOut = handleMisspelled(w);
                }
                if (!first) bw.write(" ");
                bw.write(newOut);
                first = false;
            }
            bw.newLine();
        } catch (IOException ignored) {
        }
    }
    // You can of course write other methods as well.
  }