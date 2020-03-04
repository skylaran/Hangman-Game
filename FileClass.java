package hangman;

import java.io.*;
import java.util.*;

/**
 * This class read the file(e.g. word.txt) and 
 * ensure that the computer never chooses a word that contains any of the following:
 * 
 * Upper case letters
 * Abbreviation- designed by '.'
 * An apostrophe
 * A hyphen
 * Compound words
 * A digit
 * 
 * The class also has some static methods which could provide a words list from the file without
 * unwanted expression or a legal word from the file.
 * The outcome will be used in the Hangman class.
 * @author skylaran_haoyuhan
 *
 */
public class FileClass {

	/**
	 * Reads the file and get the content(a list of words)
	 * @param file
	 * @return a list of words
	 */
	public static ArrayList<String> readFileGetList(File file) {
		
		// create ArrayList to store the words 
		ArrayList<String> wordsList = new ArrayList<String>();
		
		// instantiate FileReader and BufferedReader
		FileReader fileReader = null;
		BufferedReader bufferedReader = null;
		
		String s = "";
		
		// Read the file 
		try{
			fileReader = new FileReader(file);
			bufferedReader = new BufferedReader(fileReader);

			// Read the contents 
			while((s = bufferedReader.readLine()) != null) {
				wordsList.add(s);
			}
			
		}catch(FileNotFoundException e) {
			System.out.println("File is not found!");
		}catch(IOException e) {
			e.printStackTrace();
		}finally {
			
			// clean up file and buffered reader
			try {
				fileReader.close();
				bufferedReader.close();				
			}catch(IOException e) {
				e.printStackTrace();
			}
		}
		
		return wordsList;
	}
	
	/**
	 * Reads file and gets a list of words
	 * then remove the illegal words from the list
	 * and randomly pick one word from the list for the game
	 * @param file
	 * @return a legal word
	 */
	public static String readFileGetOneLegalWord(File file) {
		
		// create ArrayList to store the content
		ArrayList<String> WholeList = FileClass.readFileGetList(file);
		
		// create a string to store the random word
		String word = "";
		
		// removes the unwanted words from the list
		FileClass.removeIllegalWords(WholeList);
		
		// get the legal words list
		word = FileClass.GetOneWord(WholeList);
		
		return word;
	}
	
	/**
	 * Removes the unwanted expression for the list
	 * @param wordsList
	 */
	public static void removeIllegalWords(ArrayList<String> wordsList) {
		
		// create Iterator and use it to remove items
		Iterator<String> iterator = wordsList.iterator();
		while(iterator.hasNext()) {
			String next = iterator.next();
			
			// check whether the word contains unwanted expression
			if(next.contains(".")|| next.contains("'")||next.contains("-")
					|| next.contains(" ")) {
				// remove the illegal words
				iterator.remove();
			}
			
			// check whether the word has number
			else if (FileClass.CheckNumber(next)) {
				iterator.remove();
			}
			
			// check whether the word has Upper case letter
			else if(FileClass.CheckUpperCaseLetter(next)) {
				iterator.remove();
			}
		}
		
	}
	
	/**
	 * check whether the word contains digits  
	 * @param string
	 * @return true if string contains digits, false otherwise
	 */
	public static boolean CheckNumber(String string) {
		// local variable
		boolean hasNumber = false;
		
		// create regular expression which represents the number
		String regex = "(.)*(\\d+)(.)*";
				
		// check if string contains number
		if(string.matches(regex)) {
			hasNumber = true;
		}else {
			hasNumber = false;
		}
				
		return hasNumber;
	}
	
	/**
	 * check whether the word contains Upper case letter
	 * @param string
	 * @return true if string contains Upper Case Letter, false otherwise
	 */
	public static boolean CheckUpperCaseLetter(String string) {
		// local variable 
		boolean hasUpperCaseLetter = false;
		String regex = "(.)*[A-Z](.)*";
		
		if(string.matches(regex)) {
			hasUpperCaseLetter = true;
		}else {
			hasUpperCaseLetter = false;
		}
		
		return hasUpperCaseLetter;
	}

	/**
	 * Get one word from the words list
	 * @param wordsList
	 * @return one word
	 */
	public static String GetOneWord(ArrayList<String> wordsList) {
		// local variable
		String word = null;
		boolean outofbound = true;
		Random rand = new Random();
		int index = rand.nextInt(wordsList.size());
		
		while(outofbound) {
			try {
				// randomly pick up a word from wordsList
				word = wordsList.get(index);
				outofbound = false;
			}catch(Exception e) {
				System.out.println("Out of bound! Get a new Random number");		
			}			
		}

		return word;
	}
	
}
