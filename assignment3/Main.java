/* WORD LADDER Main.java
 * EE422C Project 3 submission by
 * Replace <...> with your actual data.
 * <Student1 Name>
 * <Student1 EID>
 * <Student1 5-digit Unique No.>
 * <Student2 Name>
 * <Student2 EID>
 * <Student2 5-digit Unique No.>
 * Slip days used: <0>
 * Git URL:
 * Fall 2020
 */


package assignment3;
import java.util.*;
import java.io.*;

public class Main {
	/*
		Create a linked list for words that are off by one letter? 
	 */
	static Set<String> dictionary;
	
	public static void main(String[] args) throws Exception {
		
		Scanner kb;	// input Scanner for commands
		PrintStream ps;	// output file, for student testing and grading only
		// If arguments are specified, read/write from/to files instead of Std IO.
		if (args.length != 0) {
			kb = new Scanner(new File(args[0]));
			ps = new PrintStream(new File(args[1]));
			System.setOut(ps);			// redirect output to ps
		} else {
			kb = new Scanner(System.in);// default input from Stdin
			ps = System.out;			// default output to Stdout
		}
		initialize();
		ArrayList<String> input = parse(kb);

		ArrayList<String> test = getAdjacentWords(input.get(0), dictionary);
		System.out.println(test);
	}
	
	public static void initialize() {
		dictionary = makeDictionary();
	}
	
	/**
	 * @param keyboard Scanner connected to System.in
	 * @return ArrayList of Strings containing start word and end word. 
	 * If command is /quit, return empty ArrayList. 
	 */

	public static ArrayList<String> getAdjacentWords(String word, Set<String> dictionary) {
		ArrayList<String> adjacentWords = new ArrayList<>();
		char[] charArray = word.toCharArray();

		for(int i = 0; i < charArray.length; i++) {
			char[] copyArray = charArray.clone();
			for(int j = 'A'; j <= 'Z'; j++) {
				if(charArray[i] == (char)j)
					continue;
				copyArray[i] = (char)j;
				String modifiedString = new String(copyArray);
				modifiedString.toUpperCase();
				if(dictionary.contains(modifiedString)) {
					adjacentWords.add(modifiedString);
					//dictionary.remove(modifiedString);
				}
			}
		}

		return adjacentWords;
	}

	public static ArrayList<String> parse(Scanner keyboard) {
		System.out.println("Start word, end word");
		String startword = keyboard.next();
		if(startword.equals("/quit")) {
			return new ArrayList<String>();
		}
		String endword = keyboard.next();
		if(endword.equals("/quit")) {
			return new ArrayList<String>();
		}
		ArrayList<String> words = new ArrayList<String>();
		words.add(startword.toUpperCase());
		words.add(endword.toUpperCase());
		return words;
	}
	
	public static ArrayList<String> getWordLadderDFS(String start, String end) {


		
		return null; // replace this line later with real return
	}
	
    public static ArrayList<String> getWordLadderBFS(String start, String end) {
		
		// TODO some code
		
		return null; // replace this line later with real return
	}
    
	
	public static void printLadder(ArrayList<String> ladder) {
		
	}
	// TODO
	// Other private static methods here


	/* Do not modify makeDictionary */
	public static Set<String>  makeDictionary () {
		Set<String> words = new HashSet<String>();
		Scanner infile = null;
		try {
			infile = new Scanner (new File("short_dict.txt"));
		} catch (FileNotFoundException e) {
			System.out.println("Dictionary File not Found!");
			e.printStackTrace();
			System.exit(1);
		}
		while (infile.hasNext()) {
			words.add(infile.next().toUpperCase());
		}
		return words;
	}
}
