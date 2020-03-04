package hangman;

import java.util.ArrayList;

/**
 * Hangman is a 2-player word-guessing game generally played by having one player 
 * think a word and the other player tring to guess the word letter by letter.
 * 
 * The program is a computer versus user version of the game.
 * The computer will pick a random word from a dictionary file.
 * 
 * We have 2 version of the game: Traditional version , Evil version
 * The details of the game is in the subclasses
 * 
 * This is an abstract class which contains some same methods for both versions
 * and an abstract method for each class
 * @author skylaran_haoyuhan
 *
 */
public abstract class Hangman {
	// Variables
	
	// Words list setup
	ArrayList<String> wordsList;	
	
	// create ArrayList to store the scanner input
	ArrayList<String> input = new ArrayList<String>();
	ArrayList<String> blanks= new ArrayList<String>();
	
	// records the times of wrong guess
	int WrongGuessCount;
	
	// represents the file name
	String fileName;
	
	// Constructor
	public Hangman(String file) {
		this.fileName = file;
		this.WrongGuessCount = 0;
		this.wordsList = new ArrayList<String>();
	}
	
	// Methods
	/**
	 * this method is the main function of Hangman game
	 * it will setup the game, get the user input and print the game result
	 * and get next guessed letter, continue process above till the word is right guessed 
	 * or the man is hung to death(Wrong guess 8 times).
	 */
	public abstract void PlayHangman();
	
	/**
	 * Check if the words contains the input letter
	 * @param updated wordList
	 * @param players' input letter
	 * @return true if the word contains the letter, false otherwise
	 */
	public boolean HangmanResult(ArrayList<String> wordList, String s) {
		return wordList.get(0).contains(s);
	}
	
	/**
	 * Print the result of the guess. For example, a word "read" with an user's input letter "e"
	 * print the result "_ e _ _", and if next input is "a" , print the result "_ e a _"
	 * @param words updated words list
	 * @param blanks: list of "_" with the same length of the word 
	 * @param input: an list of the input, which contains the previous input letters
	 * @return true if the words contain the latest input letter, false otherwise
	 */
	public boolean printResult(ArrayList<String> words,ArrayList<String> blanks, ArrayList<String> input) {
		// store the word 
		String[] word = words.get(0).split("");
		
		// loop through input ArrayList
		for(int index = 0; index < input.size(); index ++) {
			input.get(index);
			for(int j = 0 ; j < word.length ; j++) {
				if(word[j].equals(input.get(index))){
					blanks.set(j, input.get(index));
				}
			}
		}
		// print the blanks with the right letters
		for(String b: blanks) {
			System.out.print(b+" ");
		}
		System.out.println();
		
		return words.get(0).contains(input.get(input.size()-1));
	}
	
	/**
	 * check whether the game is over
	 * @return true if players guess the entire word or the wrong guess time over 8. false otherwise
	 */
	public boolean GameIsOver() {
		boolean GameOver = true;
		
		// if blanks still have "_" or WrongGuessCount is still smaller than 8, game is not over
		// Or, when game just start, blanks array is zero, game just starts 
		if((this.blanks.contains("_") || this.blanks.size() == 0) && this.WrongGuessCount < 8) {
			GameOver = false;
		}
		
		return GameOver;
	}
}
