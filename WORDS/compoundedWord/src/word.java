import java.util.Scanner;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.io.File;
import java.io.FileNotFoundException;

// Helper data structure for class word each node, which contains a letter as its value, in trie may have a list of children nodes.
// Trie is also able to find all suffixes indices of a word
class Trie {
    // Inner class, only for the use of Trie
    private class TrieNode {
        private char val;			// character stored in the node
        private HashMap<Character, TrieNode> children;    	// map name of string to the node
        // which has the string as value
        private boolean isWord;		// if the node is at the end of a word
        // constructor
        public TrieNode(char val) {
            this.val = val;
            children = new HashMap<Character, TrieNode>();
            isWord = false;
        }

        // Add child to trie node
        public void addChild(char child) {
            children.put(child, new TrieNode(child));
        }

        // Get child of trie node that has the same character as the give one
        public TrieNode getChild(char child) {
            if (!children.keySet().contains(child)) {
                return null;
            }
            return children.get(child);
        }
        // return true if child exists
        public boolean containsChild(char child) {
            return children.keySet().contains(child);
        }
    }

    private TrieNode root;
    private TrieNode curr;

    public Trie() {
        root = new TrieNode(' ');	// root
        curr = root;
    }

    // Insert a word to trie
    public void insert(String s) {
        char letter;
        curr = root;

        // traverse every letter of a word
        // update trie if a letter is not in the structure
        for (int i = 0; i < s.length(); i++) {
            letter = s.charAt(i);
            if (!curr.containsChild(letter)) {
                curr.addChild(letter);
            }
            curr = curr.getChild(letter);
        }
        // mark last letter as the end of a word
        curr.isWord = true;
    }

    // Return starting indices of all suffixes of a word
    public List<Integer> getSuffixesStartIndices(String s) {
        List<Integer> indices = new LinkedList<>();	// store indices
        char letter;
        curr = root;	// start from root

        for (int i = 0; i < s.length(); i++) {
            letter = s.charAt(i);

            // If the current letter doesn't have one letter as child, which means trie currently doesn't have the relationship
            // returns indices of suffixes
            if (!curr.containsChild(letter))
                return indices;

            // move on to the child node
            curr = curr.getChild(letter);

            // If the letter is an end to a word, it means after the letter is a suffix
            // update indices
            if (curr.isWord)
                indices.add(i + 1);
        }

        return indices;
    }

}
class Pair<T> {

    private T first;
    private T second;

    public Pair(T first, T second) {
        this.first = first;
        this.second = second;
    }

    // return first element
    public T first() {
        return first;
    }

    // return second element
    public T second() {
        return second;
    }
}

public class word {

    public static void main(String[] args) throws FileNotFoundException {
        // Stores the starting time
        long startTime = System.currentTimeMillis();

        // Getting input : change file name accordingly
        File file = new File("Input_01.txt");

        // Trie
        Trie trie = new Trie();
        LinkedList<Pair<String>> queue = new LinkedList<>();

        // Scan the file
        Scanner s = new Scanner(file);

        String word;				// a word
        List<Integer> sufIndices;	// indices of suffixes of a word

        // Read words from the file
        // fill up the queue with words which have suffixes, who are candidates to be compound words
        // insert each word in trie
        while (s.hasNext()) {
            word = s.next();
            sufIndices = trie.getSuffixesStartIndices(word);
            for (int i : sufIndices) {
                if (i >= word.length())		// if index is out of bound
                    break;					// it means suffixes of the word has
                // been added to the queue if there is any
                queue.add(new Pair<String>(word, word.substring(i)));
            }
            trie.insert(word);
        }

        Pair<String> p;				// a pair of word and its remaining suffix
        int maxLength = 0;			// longest compound word length
        String longest = "";		// longest compound word
        String sec_longest = "";	// second-longest compound word

        while (!queue.isEmpty()) {
            p = queue.removeFirst();
            word = p.second();
            sufIndices = trie.getSuffixesStartIndices(word);

            // if no suffixes found, which means no prefixes found
            // discard the pair and check the next pair
            if (sufIndices.isEmpty()) {
                continue;
            }
            for (int i : sufIndices) {
                if (i > word.length()) {
                    break;
                }
                if (i == word.length()) { // no suffix, means it is a compound word
                    // check if the compound word is the longest
                    // if it is update both longest and second longest
                    // words records
                    if (p.first().length() > maxLength) {
                        //sec_maxLength = maxLength;
                        sec_longest = longest;
                        maxLength = p.first().length();
                        longest = p.first();
                    }

                } else {
                    queue.add(new Pair<String>(p.first(), word.substring(i)));
                }
            }
        }

        //Store the ending time
        long endTime = System.currentTimeMillis();
        // Print out the results
        System.out.println("Longest Compound Word : " + longest);
        System.out.println("Second Longest Compound Word : " + sec_longest);
        System.out.println("Time Taken : " + (endTime - startTime) + " ms");

    }
}
