package hangman;

import java.util.Random;
import java.util.Scanner;

/**
 * This controller class launches the game and takes user input
 * @author skylaran_haoyuhan
 */
public class HangmanControll {

	/**
	 * main method pass the user input to the program 
	 * and randomly setup Evil version or traditional version
	 * start a new round if player enter "Y"
	 * game ends when player enter "N";
	 * @param args
	 */
	public static void main(String[] args) {
		String answer = "";
		
		Scanner scanner = new Scanner(System.in);
		Random rand = new Random();
		
		// randomly run evil version or traditional version
		// and tell the player which version he/she is playing after each round.
		boolean dice = rand.nextBoolean();
		while(true) {
			if(dice) {
				EvilHangman evil1 = new EvilHangman("words.txt");
				evil1.PlayHangman();			
				System.out.println("You played Evil Hangman! It's tough, haha~");
			}else{
				TraditionalHangman trad1 = new TraditionalHangman("words.txt");
				trad1.PlayHangman();
				System.out.println("You played Traditional Hangman! It's not that hard :)");
			}
			
			System.out.println("Do you wanna challenge again?(Y/N)");
			answer = scanner.next();
		
			// keep playing when answer is yes
			if(answer.equals("Y")) {
				System.out.println();
				continue;
			}else {
				System.out.println("Bye Bye");
				break;
			}
		}
		
		scanner.close();
	}

}
