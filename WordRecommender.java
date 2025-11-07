import java.util.ArrayList;
import java.util.BitSet;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashSet;

public class WordRecommender {

    private final ArrayList<String> dictionary = new ArrayList<>();
    private final HashSet<String> wordSet = new HashSet<>();

    public WordRecommender(String dictionaryFile) {
        try (BufferedReader br = new BufferedReader(new FileReader(dictionaryFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                String word = line.trim();
                if (!word.isEmpty()) {
                    dictionary.add(word);
                    wordSet.add(word);
                }
            }
        } catch (Exception ignored) {
        }
    }

    public ArrayList<String> getWordSuggestions(String word,
                                                int tolerance,
                                                double commonPercent,
                                                int topN) {
        ArrayList<String> best = new ArrayList<>();
        ArrayList<Double> scores = new ArrayList<>();

        for (String candidate : dictionary) {
            if (Math.abs(candidate.length() - word.length()) > tolerance) continue;

            double common = getCommonCharPercent(word, candidate);
            if (common < commonPercent) continue;

            double sim = getSimilarity(word, candidate);

            int pos = 0;
            while (pos < scores.size() && scores.get(pos) > sim) pos++;
            while (pos < scores.size() && Math.abs(scores.get(pos) - sim) < 1e-9
                    && best.get(pos).compareTo(candidate) <= 0) {
                pos++;
            }
            if (pos < topN) {
                best.add(pos, candidate);
                scores.add(pos, sim);
                if (best.size() > topN) {
                    best.remove(best.size() - 1);
                    scores.remove(scores.size() - 1);
                }
            }
        }
        return best;
    }

    public boolean isInDictionary(String word) {
        return wordSet.contains(word);
    }

    public double getSimilarity(String a, String b) {
        int left = leftSimilarity(a, b);
        int right = rightSimilarity(a, b);
        return (left + right) / 2.0;
    }

    public int leftSimilarity(String a, String b) {
        int n = Math.min(a.length(), b.length());
        int count = 0;
        for (int i = 0; i < n; i++) {
            if (a.charAt(i) == b.charAt(i)) count++;
        }
        return count;
    }

    public int rightSimilarity(String a, String b) {
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

    public double getCommonCharPercent(String a, String b) {
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