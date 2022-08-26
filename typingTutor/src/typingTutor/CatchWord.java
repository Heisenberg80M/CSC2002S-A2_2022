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
			int lowest = 0;
			while (i<noWords) {
					while(pause.get()) {};
					if (words[i].matchWord(target)) {
							sameWords.add(i);
					}
				  i++;
			}
			if(sameWords.size()==0){
					return;
			}
			else if(sameWords.size()==1){
					//remove that word from screen
					words[sameWords.get(0)].resetWord();
					System.out.println( " score! '" + target); //for checking
					score.caughtWord(target.length());
					sameWords.clear();
					return;

			}
			else if(sameWords.size()>1){
					for(int j = 0; j<(sameWords.size()-2); j++){
								if(words[sameWords.get(j)].getY()>words[sameWords.get(j+1)].getY()){
										lowest = sameWords.get(j+1);
								}
								else if(words[sameWords.get(j)].getY()<words[sameWords.get(j+1)].getY()){
										lowest = sameWords.get(j);
								}
					}
					//remove that word from screen
					words[lowest].resetWord();
					System.out.println( " score! '" + target); //for checking
					score.caughtWord(target.length());
					sameWords.clear();
					return;

			}
		}
}
