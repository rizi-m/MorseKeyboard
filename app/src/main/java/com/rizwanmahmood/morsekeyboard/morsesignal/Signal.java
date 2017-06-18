package com.rizwanmahmood.morsekeyboard.morsesignal;

public class Signal {

    /**
     * Checks whether the signal is in a certain range of time
     * @param min
     * @param max
     * @param signal
     * @return true if it is in the range and max is bigger than minimum and max is bigger than 0
     */
    private static boolean valid(long min, long max, long signal) {
        return signal >= min && signal<= max && max > min && max > 0;
    }

    /**
     * Checks whether the signal is long enough
     * @param min
     * @param signal
     * @return true if it is bigger than the minimum time given and the minimum time is bigger than 0
     */
    private static boolean valid(long min, long signal) {
        return signal >= min && min > 0;
    }

    /**
     * checks if it is a dot for a medium speed
     * @param signal the signal given is miliseconds
     * @return true if it falls in a certain time range
     */
    private static boolean dot_medium(long signal) {
        return valid(0, 200, signal);
    }

    /**
     * checks if it is a dash for a medium speed
     * @param signal the signal given is miliseconds
     * @return true if it falls in a certain time range
     */
    private static boolean dash_medium(long signal) {
        return valid(201, 600, signal);
    }

    /**
     * checks if it is a dash for a medium speed
     * @param signal the signal given is miliseconds
     * @return true if it falls in a certain time range
     */
    private static boolean space_medium(long signal) {
        return valid(601, signal);
    }

    //String values for dot, dash and space
    public static final String DOT = "dot";
    public static final String DASH = "dash";
    public static final String SPACE = "space";

    //values for slow medium and fast typing speed
    public static final int SLOW = 0;
    public static final int MEDIUM = 1;
    public static final int FAST = 2;

    /**
     * checks what the signal is and returns the appropriate string value
     * @param signal
     * @param speed
     * @return
     */
    public static String checkSignal(long signal, int speed) {
        switch(speed) {
            case SLOW:
                break;
            case MEDIUM:
                if(dot_medium(signal)) return DOT;
                if(dash_medium(signal)) return DASH;
                if(space_medium(signal)) return SPACE;
                break;
            case FAST:
                break;
            default:
                break;
        }
        return "";
    }

}
