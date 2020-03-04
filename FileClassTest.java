package hangman;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test the non-private methods from the FileClass class.
 * 
 * test methods include:
 * void testReadFileGetList() {...}
 * void testReadFileGetOneLegalWord() {...}
 * void testRemoveIllegalWords() {...}
 * void testCheckNumber() {...}
 * void testCheckUpperCaseLetter() {...}
 * void testGetOneWord() {...}
 * 
 * @author skylaran_haoyuhan
 */
class FileClassTest {
	// create instances
	File legalfile;
	ArrayList<String> illegalWordsList;
	ArrayList<String> wordsList;
	File wholefile;
	
	// store regular expression
	String regex;
	String word;
	
	@BeforeEach
	void setUp() throws Exception {
		legalfile = new File("words_clean.txt");
		illegalWordsList = new ArrayList<String>();
		regex = "";
		word = "";
	}

	/**
	 * Test whether the method could get a words list with all the words in the file
	 */
	@Test
	void testReadFileGetList() {
		wordsList = FileClass.readFileGetList(legalfile);
		
		// "words_clean.txt" has 57 words
		assertEquals(57,wordsList.size());
		assertTrue(FileClass.readFileGetList(legalfile).contains(FileClass.readFileGetOneLegalWord(legalfile)));
	}

	/**
	 * test whether the method could get a word from a whole list and
	 * the word doesn't contain any unwanted expression
	 */
	@Test
	void testReadFileGetOneLegalWord() {
		wholefile = new File("words.txt");
		
		// test whether result contains unwanted expressions
		word = FileClass.readFileGetOneLegalWord(wholefile);
		assertTrue(word instanceof String);
		assertFalse(FileClass.CheckNumber(word));
		assertFalse(FileClass.CheckUpperCaseLetter(word));	
		
		regex = "[.-\\s]";
		assertFalse(word.contains(regex));
	}
	
	/**
	 * give a list of words with unwanted expression, check whether the method
	 * can remove these words. And check whether the method would remove the words 
	 * with legal expression. 
	 */
	@Test
	void testRemoveIllegalWords() {
		// create a list of illegal words
		illegalWordsList.add("Berlin");
		illegalWordsList.add("23stds");
		illegalWordsList.add("you're");
		illegalWordsList.add("mr.");
		illegalWordsList.add("user-generated");
		illegalWordsList.add("post office");
		
		FileClass.removeIllegalWords(illegalWordsList);
		assertTrue(illegalWordsList.isEmpty());
		assertEquals(0,illegalWordsList.size());
		
		illegalWordsList.add("remember");
		FileClass.removeIllegalWords(illegalWordsList);		
		assertEquals(1,illegalWordsList.size());
		assertTrue(illegalWordsList.contains("remember"));
	}

	/**
	 * test whether the method could tell the word with digits or not.
	 */
	@Test
	void testCheckNumber() {
		assertFalse(FileClass.CheckNumber("absce"));
		assertTrue(FileClass.CheckNumber("ase32df"));
		assertTrue(FileClass.CheckNumber("454578"));
	}
	
	/**
	 * test whether the method could tell the word with upper case letter
	 */
	@Test
	void testCheckUpperCaseLetter() {
		assertFalse(FileClass.CheckUpperCaseLetter("abcdef"));
		assertTrue(FileClass.CheckUpperCaseLetter("Aegs"));
	}

	/**
	 * test whether the method could give a word from the list
	 */
	@Test
	void testGetOneWord() {
		wordsList = FileClass.readFileGetList(legalfile);
		
		assertTrue(FileClass.GetOneWord(wordsList) instanceof String);
		assertTrue(wordsList.contains(FileClass.GetOneWord(wordsList)));
		
	}

}
