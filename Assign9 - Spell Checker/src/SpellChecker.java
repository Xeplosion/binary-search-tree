import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;

public class SpellChecker {
    public static void main(String[] args) {

        // Step 1: Demonstrate tree capabilities
        testTree();

        // Step 2: Read the dictionary and report the tree statistics
        BinarySearchTree<String> dictionary = readDictionary();
        reportTreeStats(dictionary);

        // Step 3: Perform spell checking
        ArrayList<String> tests = sanitizeInput("Assign9 - Spell Checker/Letter.txt");
        System.out.println("--- Checking for Misspelled Words ---");
        for (String word : tests) {
            if (!dictionary.search(word)) {
                System.out.println("'" + word + "' is misspelled");
            }
        }
    }

    /**
     * This method is used to help develop the BST, also for the grader to
     * evaluate if the BST is performing correctly.
     */
    public static void testTree() {
        BinarySearchTree<String> tree = new BinarySearchTree<>();

        //
        // Add a bunch of values to the tree
        tree.insert("Olga");
        tree.insert("Tomeka");
        tree.insert("Benjamin");
        tree.insert("Ulysses");
        tree.insert("Tanesha");
        tree.insert("Judie");
        tree.insert("Tisa");
        tree.insert("Santiago");
        tree.insert("Chia");
        tree.insert("Arden");

        //
        // Make sure it displays in sorted order
        tree.display("--- Initial Tree State ---");
        reportTreeStats(tree);

        //
        // Try to add a duplicate
        if (tree.insert("Tomeka")) {
            System.out.println("oops, shouldn't have returned true from the insert");
        }
        tree.display("--- After Adding Duplicate ---");
        reportTreeStats(tree);

        //
        // Remove some existing values from the tree
        tree.remove("Olga");    // Root node
        tree.remove("Arden");   // a leaf node
        tree.display("--- Removing Existing Values ---");
        reportTreeStats(tree);

        //
        // Remove a value that was never in the tree, hope it doesn't crash!
        tree.remove("Karl");
        tree.display("--- Removing A Non-Existent Value ---");
        reportTreeStats(tree);
    }

    /**
     * This method is used to remove punctuation from test files
     */
    public static ArrayList<String> sanitizeInput(String path) {
        ArrayList<String> words = new ArrayList<>();

        // Create a HashSet of invalid characters
        char[] characters = {'"', ',', ':', '.', '!', '?', '(', ')', ' '};
        HashSet<Character> sanitize = new HashSet<>();
        for (char c : characters) {
            sanitize.add(c);
        }

        // Read the test file
        File file = new File(path);
        try (Scanner input = new Scanner(file)) {
            sanitizeLine(input, words, sanitize);
        }
        catch ( java.io.FileNotFoundException fnfe) {
            System.out.println("File not found :(");
        }

        return words;
    }
    private static void sanitizeLine(Scanner input, ArrayList<String> words, HashSet<Character> sanatize) {
        while (input.hasNextLine()) {
            StringBuilder processed = new StringBuilder();
            String line = input.nextLine().toLowerCase();
            for (char ch : line.toCharArray()) {
                // If the character is valid append it
                if (!sanatize.contains(ch)) {
                    processed.append(ch);
                } else {
                    if (!processed.isEmpty()) words.add(processed.toString());
                    processed.setLength(0);
                }
            }
            if (!processed.isEmpty()) words.add(processed.toString()); // Add the last word in the line
        }
    }

    /**
     * This method is used to report on some stats about the BST
     */
    public static void reportTreeStats(BinarySearchTree<String> tree) {
        System.out.println("-- Tree Stats --");
        System.out.printf("Total Nodes : %d\n", tree.numberNodes());
        System.out.printf("Leaf Nodes  : %d\n", tree.numberLeafNodes());
        System.out.printf("Tree Height : %d\n", tree.height());
    }

    /**
     * This method reads the dictionary and constructs the BST to be
     * used for the spell checking.
     */
    public static BinarySearchTree<String> readDictionary() {
        BinarySearchTree<String> tree = new BinarySearchTree<>();
        ArrayList<String> words = new ArrayList<>();

        // Load words into array
        File file = new File("Assign9 - Spell Checker/Dictionary.txt");
        try (Scanner input = new Scanner(file)) {
            while (input.hasNextLine()) {
                words.add(input.nextLine());
            }
        }
        catch (java.io.IOException ex) {
            System.out.println("An error occurred trying to read the dictionary" + ex);
        }

        // Shuffle the array and add words
        java.util.Collections.shuffle(words, new java.util.Random(System.currentTimeMillis()));
        for (String word : words) {
            tree.insert(word);
        }

        return tree;
    }
}