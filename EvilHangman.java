package hangman;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;


/**
 * In the evil Hangman, the computer keeps changing the word in order to make the user's task harder.
 * The computer begins by maintaining a list of all words of particular length.
 * Whenever the player guesses, the computer partitions the words into "word families" 
 * based on the positions of the guessed letters in the words of a particular length.
 * 
 * For example, if the full word list is ECHO,HEAL,BELT,HAIRCUT and the player guesses letter "E",
 * the word families:
 * 
 * E---,containing ECHO
 * -E--,containing HEAL and BELT
 * -E---,containing HELLO
 * -------,containing HAIRCUT
 * 
 * once the words are divided into these groups the computer picks the 
 * largest of the groups(with most words) to use as its remaining word list.
 * By doing so, it gives itself the maximum chance to dodge the user's guess.
 * 
 * it then reveals the letters in the positions indicated by word family.
 * in this case, the computer would pick the family -E-- and would reveal
 * an E in the second position of the word.
 * 
 * The computer cannot reverse its decision once it has determined which letters to reveal.
 * In the example above, if the user guesses "L" next, the computer must pick a word from 
 * its word list that has four letters and contains an E in the 2nd position.
 * The computer cannot switch and pick word HELLO
 * @author skylaran_haoyuhan
 *
 */
public class EvilHangman extends Hangman {

	// constructor
	public EvilHangman(String fileName) {
		super(fileName);
	}
	
	// Methods
	
	/**
	 * this method is the main function of EvilHangman
	 * it will setup the inital words list, get the user input, update the words list
	 * and print the game result, and get next guessed letter, continue process above
	 * till the word is right guessed or the man is hung to death(Wrong guess 8 times).
	 */
	@Override
	public void PlayHangman() {
		System.out.println("Welcome to Hangman game!");
		
		// Words list setup
		this.wordsList = this.WordsListSetup(this.fileName);
		
		// get the user input 
		Scanner scanner = new Scanner(System.in);
		
		// check if gameisOver
		while(!this.GameIsOver()) {
			
			System.out.println("Please make a guess! Note: You can only input"
					+ " one letter per time. ");
			
			String string = scanner.next();
			
			// store the input
			// check whether user give a duplicated letter
			if(this.input.contains(string)) {
				System.out.println("You guess this letter before! Please give a new one!");
				continue;
			}else {
				this.input.add(string);				
			}
			
			// update the words list 
			// create an ArrayList to stop the update words list
			ArrayList<String> UpdatedWordsList = new ArrayList<String>();
			UpdatedWordsList = this.WordsListUpdate(string);
			
			// create an ArrayList with all characters "_" with the same length of the word
			if(this.blanks.isEmpty()) {
				for(String str : UpdatedWordsList.get(0).split("")) {
					this.blanks.add("_");
				}
			}
			
			// based on the user input, update and print the result
			if(!this.HangmanResult(UpdatedWordsList, string)) {
				this.WrongGuessCount += 1;
			}
			
			// print the words with right letters and "_" remaining
			this.printResult(UpdatedWordsList, this.blanks, this.input);
			System.out.println("Wrong Guess Count: " + this.WrongGuessCount);
			
			// renew the wordsList with UpdatedWordsList
			this.wordsList = UpdatedWordsList;
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
			System.out.println("The word is:" + this.wordsList);
		}
	}
	
	/**
	 * This method reads the file , gets random amounts of words from the file
	 * as the initial words list
	 * @return an ArrayList of random amounts of words
	 */
	public ArrayList<String> WordsListSetup(String fileName){
		
		// create file 
		File file = new File(fileName);
		
		// create ArrayList to store every word, and use it to check 
		// whether we get duplicated word
		ArrayList<String> wordList = new ArrayList<String>();
		
		// create String to store the ReadString
		String word = null;
		
		// random number
		Random rand = new Random();
		
		int randomNum = rand.nextInt(10);
		// get the wordsList from the file
		for (int index = 0; index < randomNum; index ++) {
			word = FileClass.readFileGetOneLegalWord(file);
			
			// check duplicated word
			// if not duplicated word, add it to the words list
			if(!wordList.contains(word)) {
				wordList.add(word);
				
			}else {
				// if words library get duplicated word, get a new word again
				index -= 1;
				continue;
			}
		}
//		System.out.println("The words List: " + wordList);
		return wordList;
	}
	
	/**
	 * According to user's input letter, regroup the words list based on the position of the letter
	 * and the length of the words. Picks the biggest group of words for next guess
	 * @param input letter, such as "e"
	 * @return An updated words list
	 */
	public ArrayList<String> WordsListUpdate(String s){
		
		// create ArrayList
		ArrayList<String> UpdateWordsList = new ArrayList<String>();
		
		// create ArrayList to store the words list
		ArrayList<String> words = this.wordsList;
		ArrayList<String> wordsWithLetter = new ArrayList<String>();
		ArrayList<String> wordsWithoutLetter = new ArrayList<String>();
		
		// first, we should divide the words into two group
		// one with input letter, one without. 
		for(int index = 0 ; index < words.size(); index++) {
			if(words.get(index).contains(s)) {
				wordsWithLetter.add(words.get(index));
			}
			else {
				wordsWithoutLetter.add(words.get(index));
			}
		}
		
		// then, we should divide words without the letter based on the length
		int length = 0;
		
		// create map to store the group
		Map<Integer,ArrayList<String>> groupsWithoutLetter = new HashMap<Integer,ArrayList<String>>(); 
		for(int i = 0; i < wordsWithoutLetter.size(); i++) {
			// get the size of the word
			length = wordsWithoutLetter.get(i).length();
			// divide the words based on their length
			if(groupsWithoutLetter.containsKey(length)) {
				groupsWithoutLetter.get(length).add(wordsWithoutLetter.get(i));
			}
			else {
				groupsWithoutLetter.put(length, new ArrayList<String>(Arrays.asList(wordsWithoutLetter.get(i))));
			}
		}
		
		// next we should divide words with letter based on the length
		Map<Integer,HashMap<ArrayList<Integer>,ArrayList<String>>> groupsWithLetter = 
				new HashMap<Integer,HashMap<ArrayList<Integer>,ArrayList<String>>>();
		
		for(int j = 0; j < wordsWithLetter.size(); j++) {
			// get the length of the words
			length = wordsWithLetter.get(j).length();
			
			// represents the index of input letter
			ArrayList<Integer> position = new ArrayList<Integer>();
			
			// gets the positions of the letter
			for(int k = 0; k < length; k++ ) {
				if(wordsWithLetter.get(j).split("")[k].equals(s)) {
					position.add(k+1);
				}
			}
			
			// divide the words with different length 
			if(groupsWithLetter.containsKey(length)) {
				//divide the words with same length based on their position
				if(groupsWithLetter.get(length).containsKey(position)) {
					groupsWithLetter.get(length).get(position).add(wordsWithLetter.get(j));
				}
				else {
					groupsWithLetter.get(length).put(position, new ArrayList<String>(Arrays.asList(wordsWithLetter.get(j))));
				}
			}else {
				HashMap<ArrayList<Integer>, ArrayList<String>> wordmap = new HashMap<ArrayList<Integer>,ArrayList<String>>(); 
				wordmap.put(position, new ArrayList<String>(Arrays.asList(wordsWithLetter.get(j))));
				groupsWithLetter.put(length, wordmap);
			}
		}
		
		// Then, we should pick the largest group 
		// from groupsWithLetter + groupsWithoutLetter
		
		//----------------------------//
		// words without letters group//
		//----------------------------//
		
		// get iterator from the map's entrySet, a set view of mappings(key,value)
		// the type for the iterator is map.Entry<String,String>
		Iterator<HashMap.Entry<Integer,ArrayList<String>>> entries = 
				groupsWithoutLetter.entrySet().iterator();

		// create ArrayList to store the largest group
		ArrayList<String> largerWordGroup1 = new ArrayList<String>();
		
		//  store the size of a previous group, uses it for comparison
		int wordsCountHistory1 = 0;
		
		while(entries.hasNext()) {
			HashMap.Entry<Integer,ArrayList<String>> entry = entries.next();
			
			int wordsCount1 = entry.getValue().size();
			
			if(wordsCount1 >= wordsCountHistory1) {
				largerWordGroup1 = new ArrayList<String>(entry.getValue());
				wordsCountHistory1 = wordsCount1;
			}
		}
		
		//-------------------------//
		// words with letters group//
		//-------------------------//

		// create ArrayList to store the largest group
		ArrayList<String> largerWordGroup2 = new ArrayList<String>();
		
		//  store the size of a previous group, uses it for comparison
		int wordsCountHistory2 = 0;
		
		// create iterator to iterate through length
		Iterator<HashMap.Entry<Integer,HashMap<ArrayList<Integer>,ArrayList<String>>>> entries1 =
				groupsWithLetter.entrySet().iterator();
		
		while(entries1.hasNext()) {
			HashMap.Entry<Integer,HashMap<ArrayList<Integer>,ArrayList<String>>> entry1 = 
					entries1.next();
			
			// loop through the value
			HashMap<ArrayList<Integer>, ArrayList<String>> wordmap = entry1.getValue();
			// create Iterator through word map
			Iterator<HashMap.Entry<ArrayList<Integer>,ArrayList<String>>> entries2 =
					wordmap.entrySet().iterator();
			while(entries2.hasNext()) {
				HashMap.Entry<ArrayList<Integer>,ArrayList<String>> entry2 = entries2.next();
				
				int wordsCount2 = entry2.getValue().size();
				
				if(wordsCount2 >= wordsCountHistory2) {
					largerWordGroup2 = new ArrayList<String>(entry2.getValue());
					wordsCountHistory2 = wordsCount2;
				}	
			}
		}
		
		/*
		 * Then compare the two largest group and update words list with the largest one,
		 */
		// create ArrayList to store the largest group
		ArrayList<String> largestWordGroup = null;
		
		if(largerWordGroup2.size() >= largerWordGroup1.size()) {
			largestWordGroup = new ArrayList<String>(largerWordGroup2);
		}
		else {
			largestWordGroup = new ArrayList<String>(largerWordGroup1);
		}
		
		// Finally, update the UpdateWordsList
		UpdateWordsList = largestWordGroup;
		
		return UpdateWordsList;
	}

}
