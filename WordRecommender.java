import java.util.ArrayList;
import java.util.BitSet;

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

    private double getCommonCharPercent(String a, String b) {
        BitSet aSet = new BitSet(26);
        BitSet bSet = new BitSet(26);

        for (int i = 0; i < a.length(); i++) {
            char c = a.charAt(i);
            if (Character.isLowerCase(c)) {
                aSet.set(c - 'a');
            }
        }

        for (int i = 0; i < b.length(); i++) {
            char c = b.charAt(i);
            if (Character.isLowerCase(c)) {
                bSet.set(c - 'a');
            }
        }

        BitSet intersect = (BitSet) aSet.clone();
        intersect.and(bSet);
        BitSet union = (BitSet) aSet.clone();
        union.or(bSet);
        int interCount = intersect.cardinality();
        int unionCount = union.cardinality();

        return unionCount == 0 ? 0.0 : (double) interCount / unionCount;
    }
    // You can of course write other methods as well.
  }