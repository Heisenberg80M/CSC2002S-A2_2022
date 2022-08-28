package typingTutor;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class HungryWordMover extends Thread {

    private HungryWord myWord;
    private AtomicBoolean done;
    private AtomicBoolean pause;
    private Score score;
    private FallingWord[] words;
    CountDownLatch startLatch; // so all can start at once

    HungryWordMover(HungryWord word) {
        myWord = word;
    }

    HungryWordMover(HungryWord word, WordDictionary dict, Score score,
        CountDownLatch startLatch, AtomicBoolean d, AtomicBoolean p) {
        this(word);
        this.startLatch = startLatch;
        this.score = score;
        this.done = d;
        this.pause = p;
    }

    public void setWords(FallingWord[] words) {
        this.words = words;
    }

    public void run() {

        try {
            System.out.println(myWord.getWord() + " waiting to start ");
            startLatch.await();
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        } // wait for other threads to start
        System.out.println(myWord.getWord() + " started");
        while (!done.get()) {
            // animate the word
            while (!myWord.disappeared() && !done.get()) {

                // If the hungry word touches others words they should disappear

                for (int i = 0; i < words.length; i++) {
                    // Starting x
                    int x1_start = myWord.getX();
                    // approx x where the word ends
                    int x1_end = myWord.getX() + myWord.getWord().length() * 16;
                    // starting y
                    int y1_start = myWord.getY();
                    // approx y where the word ends
                    int y1_end = myWord.getY() + 16;

                    int x2_start = words[i].getX();
                    int x2_end = words[i].getX() + words[i].getWord().length() * 16;
                    int y2_start = words[i].getY();
                    int y2_end = words[i].getY() + 16;

                    if ((x1_end >= x2_start && x1_start <= x2_end) && (y2_end >= y1_start && y2_start <= y1_end)) {
                        words[i].resetWord();
                        score.missedWord();
                    } else if ((x1_end >= x2_start && x1_start <= x2_end)
                            && (y1_end >= y2_start && y1_start <= y2_end)) {
                        words[i].resetWord();
                        score.missedWord();
                    } else if ((x2_end >= x1_start && x2_start <= x1_end)
                            && (y2_end >= y1_start && y2_start <= y1_end)) {
                        words[i].resetWord();
                        score.missedWord();
                    } else if ((x2_end >= x1_start && x2_start <= x1_end)
                            && (y1_end >= y2_start && y1_start <= y2_end)) {
                        words[i].resetWord();
                        score.missedWord();
                    }

                }
                myWord.move(20);
                try {
                    sleep(myWord.getSpeed());
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                ;
                while (pause.get() && !done.get()) {
                }
                ;
            }
            if (!done.get() && myWord.disappeared()) {
                score.missedWord();
                myWord.resetWord();
            }
            myWord.resetWord();
        }
    }

}
