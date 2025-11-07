import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;

public class WordRecommenderTest {

    // isInDictionary

    @Test
    public void testIsInDictionaryTrue() {
        WordRecommender wr = new WordRecommender("engDictionary.txt");
        assertTrue(wr.isInDictionary("aardvark"));
    }

    @Test
    public void testIsInDictionaryFalse() {
        WordRecommender wr = new WordRecommender("engDictionary.txt");
        assertFalse(wr.isInDictionary("aardvarkaardvark"));
    }

    // leftSimilarity

    @Test
    public void testLeftSimilarityFullOverlap() {
        WordRecommender wr = new WordRecommender("engDictionary.txt");
        double l = wr.leftSimilarity("apple", "apple");
        assertEquals(5, l);
    }

    @Test
    public void testLeftSimilarityPartialOverlap() {
        WordRecommender wr = new WordRecommender("engDictionary.txt");
        double l = wr.leftSimilarity("apple", "a");
        assertEquals(1, l);
    }

    @Test
    public void testLeftSimilarityNoOverlap() {
        WordRecommender wr = new WordRecommender("engDictionary.txt");
        double l = wr.leftSimilarity("apple", "yes");
        assertEquals(0, l);
    }

    // rightSimilarity

    @Test
    public void testRightSimilarityFullOverlap() {
        WordRecommender wr = new WordRecommender("engDictionary.txt");
        double r = wr.rightSimilarity("apple", "apple");
        assertEquals(5, r);
    }

    @Test
    public void testRightSimilarityPartialOverlap() {
        WordRecommender wr = new WordRecommender("engDictionary.txt");
        double r = wr.rightSimilarity("apple", "eye");
        assertEquals(1, r);
    }

    @Test
    public void testRightSimilarityNoOverlap() {
        WordRecommender wr = new WordRecommender("engDictionary.txt");
        double r = wr.rightSimilarity("apple", "yes");
        assertEquals(0, r);
    }

    // getCommonCharPercent

    @Test
    public void testCommonCharPercentFullOverlap() {
        WordRecommender wr = new WordRecommender("engDictionary.txt");
        double w = wr.getCommonCharPercent("aardvark", "aardvark");
        assertEquals(1.0, w, 1e-9);
    }

    @Test
    public void testCommonCharPercentPartialOverlap() {
        WordRecommender wr = new WordRecommender("engDictionary.txt");
        double w = wr.getCommonCharPercent("committee", "comet");
        assertEquals(5.0 / 6.0, w, 1e-9);
    }

    @Test
    public void testCommonCharPercentNoOverlap() {
        WordRecommender wr = new WordRecommender("engDictionary.txt");
        double w = wr.getCommonCharPercent("aardvark", "test");
        assertEquals(0.0, w, 1e-9);
    }

    // getSimilarity

    @Test
    public void testSimilarityPartialMatch() {
        WordRecommender wr = new WordRecommender("engDictionary.txt");
        double sim = wr.getSimilarity("oblige", "oblivion");
        assertEquals(2.5, sim, 1e-9);
    }

    @Test
    public void testSimilarityExactMatch() {
        WordRecommender wr = new WordRecommender("engDictionary.txt");
        double sim = wr.getSimilarity("match", "match");
        assertEquals(5.0, sim, 1e-9);
    }

    @Test
    public void testSimilarityNotMatch() {
        WordRecommender wr = new WordRecommender("engDictionary.txt");
        double sim = wr.getSimilarity("not", "yes");
        assertEquals(0.0, sim, 1e-9);
    }

    // getWordSuggestions

    @Test
    public void testGetWordSuggestionsExample() {
        WordRecommender wr = new WordRecommender("engDictionary.txt");
        ArrayList<String> res = wr.getWordSuggestions("morbit", 2, 0.5, 4);
        assertTrue(res.contains("morbid"));
    }

    @Test
    public void testGetWordSuggestionsTopN() {
        WordRecommender wr = new WordRecommender("engDictionary.txt");
        ArrayList<String> res = wr.getWordSuggestions("automagically", 2, 0.5, 4);
        assertTrue(res.size() <= 4);
    }

    @Test
    public void testSuggestionsEmptyCase() {
        WordRecommender wr = new WordRecommender("engDictionary.txt");
        ArrayList<String> res = wr.getWordSuggestions("sleepyyyyyyyyyyyyyyyy", 2, 0.5, 4);
        assertTrue(res.isEmpty());
    }

    // constructor

    @Test
    public void testConstructorLoadsDictionary() {
        WordRecommender wr = new WordRecommender("engDictionary.txt");
        assertTrue(wr.isInDictionary("apple"));
    }

    @Test
    public void testEmptyDictionary() {
        WordRecommender wr = new WordRecommender("no_such_file.txt");
        assertFalse(wr.isInDictionary("apple"));
    }
}
