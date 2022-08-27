package typingTutor;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.ArrayList; // import the ArrayList class


//Thread to monitor the word that has been typed.
public class CatchWord extends Thread {
	String target;
	static AtomicBoolean done ; //REMOVE
	static AtomicBoolean pause; //REMOVE

	private static  FallingWord[] words; //list of words
	private static  ArrayList<Integer> sameWords = new ArrayList<Integer>(); //list of indecies of common words in words array
	private static int noWords; //how many
	private static Score score; //user score

	CatchWord(String typedWord) {
		target=typedWord;
	}

	public static void setWords(FallingWord[] wordList) {
		words=wordList;
		noWords = words.length;
	}

	public static void setScore(Score sharedScore) {
		score=sharedScore;
	}

	public static void setFlags(AtomicBoolean d, AtomicBoolean p) {
		done=d;
		pause=p;
	}

	public void run() {
			int i = 0;
			FallingWord lowest;

			while (i<noWords) {
					while(pause.get()) {};
					if (words[i].matchWord(target)) {
							lowest = words[i]; //save the matched falling word and look for a duplicate
							while(i<noWords){
									if(words[i].matchWord(target)){ // continue to look for another match
										if(lowest.getY()<words[i].getY()){
											lowest=words[i]; //save the one that is lower on the screen
										}
									}
									i++;
								}
				lowest.resetWord();
				System.out.println( " score! '" + target); //for checking
				score.caughtWord(target.length());
				//FallingWord.increaseSpeed();
				break;
			}
		i++;
		}
	}
}
