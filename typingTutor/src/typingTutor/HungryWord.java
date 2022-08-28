package typingTutor;

public class HungryWord {

    private String word; // the word
    private int x; // position - width
    private int y; // postion - height
    private int maxX; // maximum height
    private boolean disappeared; // flag for if user does not manage to catch word in time

    private int movingSpeed; // how fast this word is
    private static int maxWait = 1000;
    private static int minWait = 100;

    public static WordDictionary dict;

    HungryWord() { // constructor with defaults
        word = "computer"; // a default - not used
        x = 0;
        y = 0;
        maxX = 300;
        disappeared = false;
        movingSpeed = (int) (Math.random() * (maxWait - minWait) + minWait);
    }

    HungryWord(String text) {
        this();
        this.word = text;
    }

    HungryWord(String text, int maxX) {
        this(text);
        this.maxX = maxX;
    }

    public static void increaseSpeed() {
        minWait += 50;
        maxWait += 50;
    }

    public static void resetSpeed() {
        maxWait = 1000;
        minWait = 100;
    }

    // all getters and setters must be synchronized
    public synchronized void setY(int y) {
        this.y = y;
    }

    public synchronized void setX(int x) {
        if (x > maxX) {

            disappeared = true;
            x = maxX;
        }
        this.x = x;
    }

    public synchronized void setWord(String text) {
        this.word = text;
    }

    public synchronized String getWord() {
        return word;
    }

    public synchronized int getX() {
        return x;
    }

    public synchronized int getY() {
        return y;
    }

    public synchronized void setMaxX(int maxX) {
        this.maxX = maxX;
    }

    public synchronized int getSpeed() {
        return movingSpeed;
    }

    public synchronized void setPos(int x, int y) {
        setY(y);
        setX(x);
    }

    public synchronized void resetPos() {
        setX(0);
        ;
    }

    public synchronized void resetWord() {
        resetPos();
        word = dict.getNewWord();
        disappeared = false;
        movingSpeed = (int) (Math.random() * (maxWait - minWait) + minWait);
        // System.out.println(getWord() + " falling speed = " + getSpeed());
    }

    public synchronized boolean matchWord(String typedText) {
        // System.out.println("Matching against: "+text);
        if (typedText.equals(this.word)) {
            resetWord();
            return true;
        } else
            return false;
    }

    public synchronized void move(int inc) {
        setX(x + inc);
    }

    public synchronized boolean disappeared() {
        return disappeared;
    }

}
