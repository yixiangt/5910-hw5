import java.util.ArrayList;

public class WordRecommender {

    public WordRecommender(String dictionaryFile) {
      // TODO: change this!
    }
  
    public ArrayList<String> getWordSuggestions(String word, int tolerance, double commonPercent, int topN) {
      // TODO: change this!
      return null;
    }

    public double getSimilarity(String a, String b) {
        int left = leftSimilarity(a, b);
        int right = rightSimilarity(a, b);
        return (left + right) / 2.0;
    }

    private int leftSimilarity(String a, String b) {
        int n = Math.min(a.length(), b.length());
        int count = 0;
        for (int i = 0; i < n; i++) {
            if (a.charAt(i) == b.charAt(i)) count++;
        }
        return count;
    }

    private int rightSimilarity(String a, String b) {
        int i = a.length() - 1;
        int j = b.length() - 1;
        int count = 0;
        if (i >= 0 && j >= 0) do {
            if (a.charAt(i) == b.charAt(j)) count++;
            i--;
            j--;
        } while (i >= 0 && j >= 0);
        return count;
    }
    // You can of course write other methods as well.
  }