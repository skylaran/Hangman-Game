package hangman;

import static org.junit.jupiter.api.Assertions.*;
import java.util.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests the methods in abstract class Hangman.
 * test method include constructor and the non-static methods in the class.
 * 
 * test methods:
 * void testHangman(){...}
 * void testHangmanResult(){...}
 * void testPrintResult() {...}
 * void testGameIsOver() {...}
 * 
 * @author skylaran_haoyuhan
 */
class HangmanTest {
	TraditionalHangman traditionalHangman;
	EvilHangman evilHangman;
	
	ArrayList<String> wordsList;
	ArrayList<String> blanks;
	ArrayList<String> input;

	String s;
	int WrongGuessCount;
	@BeforeEach
	void setUp() throws Exception {
		traditionalHangman = new TraditionalHangman("words_clean.txt");
		evilHangman = new EvilHangman("words_clean.txt");
		wordsList = new ArrayList<String>();
		blanks = new ArrayList<String>();
		input = new ArrayList<String>();
		WrongGuessCount = 0;
	}

	/**
	 * test the constructor
	 */
	@Test
	void testHangman() {
		assertEquals("words_clean.txt",traditionalHangman.fileName);
		assertEquals("words_clean.txt",evilHangman.fileName);
		assertEquals(0,traditionalHangman.WrongGuessCount);
		assertEquals(0,traditionalHangman.WrongGuessCount);	
	}

	/**
	 * test whether the method could tell the word contains the letter
	 */
	@Test
	void testHangmanResult() {
		wordsList.add("heal");
		wordsList.add("read");
		s = "e";
		
		assertEquals(true,traditionalHangman.HangmanResult(wordsList, s));
		assertEquals(true,evilHangman.HangmanResult(wordsList, s));
		
		s = "s";
		assertFalse(traditionalHangman.HangmanResult(wordsList, s));
		assertFalse(evilHangman.HangmanResult(wordsList, s));
	}

	/**
	 * test whether the method could tell the word contains the latest letter
	 * and whether it could print the result of the guess
	 */
	@Test
	void testPrintResult() {
		wordsList.add("size");
		wordsList.add("live");
		if(blanks.isEmpty()) {
			for(int i = 0; i < wordsList.get(0).length(); i++) {
				blanks.add("_");
			}
		}
		input.add("i");
		System.out.println("Guess letter: " + input.get(input.size()-1));
		assertEquals(true,traditionalHangman.printResult(wordsList, blanks, input));
		assertEquals(true,evilHangman.printResult(wordsList, blanks, input));
		
		input.add("m");
		System.out.println("Guess letter: " + input.get(input.size()-1));		
		assertFalse(traditionalHangman.printResult(wordsList, blanks, input));
		assertFalse(evilHangman.printResult(wordsList, blanks, input));		
		
		input.add("e");
		System.out.println("Guess letter: " + input.get(input.size()-1));
		assertTrue(traditionalHangman.printResult(wordsList, blanks, input));
		assertTrue(evilHangman.printResult(wordsList, blanks, input));		
	}

	/**
	 * test whether the method could tell the game is over or not
	 */
	@Test
	void testGameIsOver() {
		traditionalHangman.WrongGuessCount = 8;
		traditionalHangman.blanks.add("_");
		traditionalHangman.blanks.add("_");
		traditionalHangman.blanks.add("_");
		assertEquals(true,traditionalHangman.GameIsOver());
		assertEquals(false,evilHangman.GameIsOver());
		
		traditionalHangman.WrongGuessCount = 3;
		assertFalse(traditionalHangman.GameIsOver());
		assertFalse(evilHangman.GameIsOver());
		
		traditionalHangman.blanks.set(0,"y");
		traditionalHangman.blanks.set(1,"e");
		traditionalHangman.blanks.set(2,"s");
		evilHangman.blanks.add("y");
		evilHangman.blanks.add("_");
		evilHangman.blanks.add("s");
		assertTrue(traditionalHangman.GameIsOver());
		assertFalse(evilHangman.GameIsOver());
		
		traditionalHangman.blanks.set(1, "_");
		assertFalse(traditionalHangman.GameIsOver());
				
	}

}
