package main.java;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * Created by ritesh on 8/28/16.
 */
public class WordCount {

    public static void main(String args[]) {


        try {
            final BufferedReader bf = new BufferedReader(new FileReader("filename"));

            try {

                List<List<String>> paragraphs = new LinkedList<>();
                List<String> paragraph =  null;

                for (String line = bf.readLine(); line != null; line = bf.readLine()) {
                    if (line.startsWith("  ")) {
                        paragraph = new ArrayList<String>();
                        paragraphs.add(paragraph);
                    }
                    if (paragraph != null)
                        paragraph.add(line);
                }

                HashMap<String, Long> map = new HashMap<>();

                ValueComparator bvc = new ValueComparator(map);

                for (final List<String> story : paragraphs) {

                    map.putAll(frequencyOfWords(story));
                }

                int maxElements = 0;

                final TreeMap<String, Long> frequencyOfWords = new TreeMap<>(bvc);

                frequencyOfWords.putAll(map);

                for (final Map.Entry<String, Long> entrySet : frequencyOfWords.entrySet()) {

                    if (maxElements > 10) {
                        break;
                    }

                    System.out.print(entrySet.getKey() + ":");
                    System.out.print(" " + entrySet.getValue());
                    System.out.println();

                    maxElements = maxElements + 1;
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static Map<String,Long> frequencyOfWords(final List<String> paragraph) {

        final Map<String, Long> frequencyOfWords =  new HashMap<>();

        for (final String line : paragraph) {

            String[] split = line.split("[ , . !]");

            for (final String words : split) {

                if(words.equals("")) {
                    continue;
                }

                if (!frequencyOfWords.containsKey(words)) {
                    frequencyOfWords.put(words, 1L);
                } else {
                    Long frequency = frequencyOfWords.get(words);
                    frequency = frequency + 1;
                    frequencyOfWords.put(words, frequency);
                }
            }
        }

        return frequencyOfWords;
    }

    static class ValueComparator implements Comparator<String> {
        Map<String, Long> base;

        public ValueComparator(Map<String, Long> base) {
            this.base = base;
        }

        // Note: this comparator imposes orderings that are inconsistent with
        // equals.
        public int compare(String a, String b) {
            if (base.get(a) >= base.get(b)) {
                return -1;
            } else {
                return 1;
            } // returning 0 would merge keys
        }
    }
}
