# Hangman-Game
Part1: Traditional Hangman
Traditional HangmanHangman is a 2-player word-guessing game generally played by having one player think of a word and the other player trying to guess that word letter by letter.Similar to Battleship, this project is a Java program a computer versus userversion of the game. The computer will pick a random word from a dictionary that I provied in the file. 

Part2: Evil Hangman
Evil HangmanThe evil version of this game basically exploits the computer’s ability to store a large amount of information. In the traditional version of the game, the computer has to stick to the original word as the user guesses. In the evil version, the computer keeps changing the wordin order to make the user’s task harder.The algorithm that drives Evil Hangman is fairly straightforward. The computer begins by maintaining a list of all words of a particular length. Whenever the player guesses, the computer partitions the words into "word families" based on the positions of the guessed letters in the wordsof a particular length. 
For example, if the full word list is ECHO, HEAL, BELT,HELLO,and HAIRCUTand the player guesses the letter 'E', then there would be fourword families:E ---, containingECHO-E --, containing HEAL and BELT-E ---, containing HELLO-------, containing HAIRCUTOnce the words are divided into these groups, the computer picks the largestof thegroups(withthemost words) to use as its remaining word list. By doing so, it gives itself the maximum chance to dodge the user’s guesses.It then reveals the letters in the positions indicated by the word family. In this case, the computer would pick the family -E --(because it has 2 wordsand the other families only have 1 word each) and would reveal an E in the second position of the word.The computer cannotreverse its decision once it has determined which letters to reveal. In the example above, if the user guesses ‘L’next, the computer must pick a word from its word list that has four letters and contains an Ein the second position. The computer cannot switch and pick the word HELLO. Since both HEAL and BELT have 
an L, the computer must pick one of these two words and reveal the Lin the correct position.
