package com.example.driver.element;

 
public class Times {
    public static final long QUARTER_SECOND_PAUSE = 250;
    public static final long HALF_SECOND_PAUSE = 500;
    public static final long SECOND_PAUSE = 1000;
    public static final long TWO_SECOND_PAUSE = 2000;
    public static final long THREE_SECOND_PAUSE = 3000;
    public static final long FIVE_SECOND_PAUSE = 5000;
    public static final long TEN_SECOND_PAUSE = 10000;
    public static final long TWENTY_SECOND_PAUSE = 20000;

    public static final int SECOND_WAIT = 1;
    public static final int THREE_SECOND_WAIT = 3;
    public static final int FIVE_SECOND_WAIT = 5;
    public static final int TEN_SECOND_WAIT = 10;
    public static final int TWENTY_SECOND_WAIT = 20;
    public static final int THIRTY_SECOND_WAIT = 30;
    public static final int ONE_MINUTE_WAIT = 60;
    public static final int TWO_MINUTE_WAIT = 120;
    public static final int THREE_MINUTE_WAIT = 180;
    public static final int TEN_MINUTE_WAIT = 600;

    public static void waitForMilliSeconds(long milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException interruptedException) {
            interruptedException.printStackTrace();
        }
    }
}
