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
	static Set<String> dictionary;

	// creating a My HashTable Dictionary
	static HashSet<String> wordMap = new HashSet<String>();

	// creating a My HashTable Dictionary
	static ArrayList<String> dfsList = new ArrayList<String>();
	static int dfsListIndex = 0;
	
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
		initialize();  //Makes Dict
		ArrayList<String> input = parse(kb);


		ArrayList<String> answer = getWordLadderDFS(input.get(0),input.get(1));
		//ArrayList<String> answer = getWordLadderBFS(input.get(0), input.get(1));
		System.out.println(answer);

	}
	
	public static void initialize() {
		dictionary = makeDictionary();
	}
	
	/**
	 * @param keyboard Scanner connected to System.in
	 * @return ArrayList of Strings containing start word and end word. 
	 * If command is /quit, return empty ArrayList. 
	 */

	public static ArrayList<String> testGetAdjWords(String word, String end, Set<String> dictionary) {
		ArrayList<String> adjacentWords = new ArrayList<>();
		char[] charArray = word.toCharArray();
		char[] endArray = end.toCharArray();

		for(int i = charArray.length - 1; i >= 0; i--) {
			if(charArray[i] == endArray[i])
				continue;
			char[] copyArray = charArray.clone();
			copyArray[i] = endArray[i];
			String modifiedString = new String(copyArray);
			modifiedString.toUpperCase();
			if(dictionary.contains(modifiedString)) {
				adjacentWords.add(modifiedString);
				dictionary.remove(modifiedString);
			}
		}

		for(int i = charArray.length - 1; i >= 0; i--) {
			char[] copyArray = charArray.clone();
			for(int j = 'A'; j <= 'Z'; j++) { //Start near
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
	public static ArrayList<String> getAdjacentWords(String word, Set<String> dictionary) {
		ArrayList<String> adjacentWords = new ArrayList<>();
		char[] charArray = word.toCharArray();

		for(int i = 0; i < charArray.length; i++) {
			char[] copyArray = charArray.clone();
			for(int j = 'A'; j <= 'Z'; j++) { //Start near
				if(charArray[i] == (char)j)
					continue;
				copyArray[i] = (char)j;
				String modifiedString = new String(copyArray);
				modifiedString.toUpperCase();
				if(dictionary.contains(modifiedString)) {
					adjacentWords.add(modifiedString);
					dictionary.remove(modifiedString);
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
		ArrayList<String> test = testGetAdjWords(start, end, dictionary);
		if(test.contains(end)) {
			dfsList.add(dfsListIndex, end);
			return dfsList;
		}
		dfsList.add(dfsListIndex, start);
		dfsListIndex++;
		wordMap.add(start);

		if (start.equals(end)) {
			return dfsList;
		}
		if (test.isEmpty()) {
			dfsListIndex--;
			if(dfsList.size() == 1)
				dfsList.add(end);
			return dfsList;
		}

		for (int i=0; i < test.size() && !dfsList.contains(end); i++) {
			if (!wordMap.contains(test.get(i))) {
				getWordLadderDFS(test.get(i), end);
				if(!dfsList.contains(end)) {
					dfsListIndex--;
					dfsList.remove(dfsListIndex);
					}
				}
			}

		if(dfsList.size() == 1)
			dfsList.add(end);
		return dfsList;
	}
	
    public static ArrayList<String> getWordLadderBFS(String start, String end) {
		HashMap<String, String> predecessorMap = new HashMap<String, String>(); //(Child, Parent)
		Set<String> bfsDictionary = makeDictionary();
		ArrayList<String> list = new ArrayList<String>();
		ArrayList<String> path = new ArrayList<String>();
		LinkedList<String> queue = new LinkedList<String>();
		queue.add(start);
		boolean flag = false;
		while(!queue.isEmpty() && !flag) {
			String currentWord = queue.removeFirst();
			ArrayList<String> adjWords = getAdjacentWords(currentWord, bfsDictionary);
			for(int i = 0; i < adjWords.size(); i++) {
				queue.add(adjWords.get(i));
				bfsDictionary.remove(adjWords.get(i));
				predecessorMap.put(adjWords.get(i), currentWord);
				if(adjWords.get(i).equals(end)) {
					flag = true;
					break;
				}
			}
		}
		if(predecessorMap.get(end) == null) {
			path.add(start);
			path.add(end);
		}
		else {
			String currentWord = end;
			while(currentWord != start) {
				list.add(currentWord);
				currentWord = predecessorMap.get(currentWord);
			}
			list.add(start);
			for(int i = 1; i < list.size() + 1; i++) {
				path.add(list.get(list.size() - i));
			}
		}

		return path; // replace this line later with real return
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
			infile = new Scanner (new File("five_letter_words.txt"));
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
