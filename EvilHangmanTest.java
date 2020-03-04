package hangman;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertEquals;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
/**
 * Test class for EvilHangman. Test the two methods: WordsListSetup, WordsListUpdate
 * @author skylaran_haoyuhan
 *
 */
class EvilHangmanTest {
    String myFile;
    EvilHangman evilHangMan;
    //setup run before each methods
	@BeforeEach
	void setUp() throws Exception {
		myFile = "words.txt";
		//This is actually also a test for the constructor
		this.evilHangMan = new EvilHangman(myFile);
	}


	/**
	 * test whether the method could get the initial words list
	 */
	@Test
	void testWordsListSetup() {
		ArrayList<String> wordsList = this.evilHangMan.WordsListSetup(myFile);
		//test that the words list is not empty
		for (String word: wordsList) {
            assertNotNull(word);
		}
		//test that each words in words list is not the same to each other
		for(int index = 0; index < wordsList.size()-1;index++) {
			assertNotEquals(wordsList.get(index), wordsList.get(index+1));
		}
    
	}

	/**
	 * test whether the method could update the words list based on the input letter 
	 * and pick the largest group of the word.
	 */
	@Test
	void testWordsListUpdate() {
		//set up the initial word group
		this.evilHangMan.wordsList = new ArrayList<String>();
		this.evilHangMan.wordsList.add("head");
		this.evilHangMan.wordsList.add("cup");
		this.evilHangMan.wordsList.add("iterator");
		this.evilHangMan.wordsList.add("hot");
		this.evilHangMan.wordsList.add("listen");
		this.evilHangMan.wordsList.add("read");
		//set up the test word group
		ArrayList<String> testWordsList = new ArrayList<String>();
		
		testWordsList.add("head");
		testWordsList.add("read");
		assertEquals(testWordsList,this.evilHangMan.WordsListUpdate("e"));
	}

}
