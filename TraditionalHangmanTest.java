package hangman;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test the methods in subclass traditionalHangman class;
 * 
 * test method includes:
 * void testPlayHangman() {...}
 * void testTraditionalHangman() {...}
 * 
 * @author skylaran_haoyuhan
 *
 */
class TraditionalHangmanTest {

	TraditionalHangman tradHangman;
	
	@BeforeEach
	void setUp() throws Exception {
		tradHangman = new TraditionalHangman("words_clean.txt");
	}
	
	/**
	 * test whether the traditional hangman game works
	 */
	@Test
	void testPlayHangman() {
		tradHangman.PlayHangman();
		
		assertTrue(!tradHangman.input.isEmpty());
		assertEquals(1, tradHangman.wordsList.size());
		assertFalse(tradHangman.blanks.contains("_") && tradHangman.WrongGuessCount < 8);
	}

	/**
	 * test traditional Hangman constructor
	 */
	@Test
	void testTraditionalHangman() {
		assertEquals("words_clean.txt",tradHangman.fileName);
		assertNotNull(tradHangman.wordsList);
		assertEquals(0,tradHangman.WrongGuessCount);
	}

}
