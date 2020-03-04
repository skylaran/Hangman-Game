package hangman;

import java.io.File;
import java.util.Scanner;

/**
 * Traditional Hangman rules:
 * Represent the word to guess by using a row of underscores representing each letter of the word
 * 
 * For example, word "dog" would be shown to the user as _ _ _ .
 * 
 * The user guesses one letter at a time. Every correct letter that they guess should 
 * be shown in its correct location(s). Every guess that is incorrect will be shown in
 * a list right underneath the word.
 * 
 * for example, word "dog" with the input letter "o"
 * The output should be _ 0 _.
 * if next guess is "d", the output should be d o _.
 * 
 * @author skylaran_haoyuhan
 *
 */
public class TraditionalHangman extends Hangman {

	// Constructor
	public TraditionalHangman(String fileName) {
		super(fileName);
	}
	
	// Methods
	/**
	 * this method is the main function of TraditionalHangman
	 * it will setup the initial word, get the user input, update the word
	 * and print the game result, and get next guessed letter, continue process above
	 * till the word is right guessed or the man is hung to death(Wrong guess 8 times).
	 */
	@Override
	public void PlayHangman() {
		System.out.println("Welcome to Hangman game!");
		
		// read the file and give the word 
		File file = new File(this.fileName);
		
		// words list setup
		// the words list is just one word
		this.wordsList.add(FileClass.readFileGetOneLegalWord(file));
		
		// get the user input
		Scanner scanner = new Scanner(System.in);	
		
		while(!this.GameIsOver()) {

			System.out.println("Please make a guess! Note: You can only input"
					+ " one letter per time. ");
			
			// get the user input
			String str = scanner.next();

			// if the players input a letter twice, friendly reminds him to guess a new letter again!
			if(this.input.contains(str)) {
				System.out.println("You guess this letter before! Please give a new one!");
				continue;
			}else {
				this.input.add(str);
			}
			
			// create an ArrayList with all characters "_" with the same length of the word
			if(this.blanks.isEmpty()) {
				for(String string : this.wordsList.get(0).split("")) {
					this.blanks.add("_");
				}
			}
			
			// based on the user input, update and print the result
			if(!this.HangmanResult(this.wordsList, str)) {
				this.WrongGuessCount += 1;
			}
			
			// print the words with right letters and "_" remaining
			this.printResult(this.wordsList, this.blanks, this.input);
			System.out.println("Wrong Guess Count: " + this.WrongGuessCount);
			
		}
		// print the final outcome
		// if WrongGuessCount is more than 8, user lose the game
		if(this.WrongGuessCount < 8) {
			System.out.println();
			System.out.println("Congratulation! You win!");
			System.out.println();
		}else {
			System.out.println();
			System.out.println("You lose!");
			System.out.println("The word is: " + this.wordsList);
		}
	}

}
