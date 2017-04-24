package com.bitdecay.game.util;

import java.util.ArrayList;
import java.util.List;

public class Xbox360Pad {
    public static final int X = 13;
    public static final int Y = 14;
    public static final int A = 11;
    public static final int B = 12;
    public static final int BACK = 5;
    public static final int START = 4;
    public static final int DPAD_UP = 0;
    public static final int DPAD_DOWN = 1;
    public static final int DPAD_RIGHT = 3;
    public static final int DPAD_LEFT = 2;
    public static final int LB = 8;
    public static final int L3 = 6;
    public static final int RB = 9;
    public static final int R3 = 7;

    public static final int LT = 100;
    public static final int RT = 101;

    public static final int LEFT = 302;
    public static final int RIGHT = 202;
    public static final int DOWN = 203;
    public static final int UP = 303;

    public static List<Integer> buttons = new ArrayList<>();
    static {
        buttons.add(X);
        buttons.add(Y);
        buttons.add(A);
        buttons.add(B);
        buttons.add(BACK);
        buttons.add(START);
        buttons.add(DPAD_UP);
        buttons.add(DPAD_DOWN);
        buttons.add(DPAD_RIGHT);
        buttons.add(DPAD_LEFT);
        buttons.add(LB);
        buttons.add(L3);
        buttons.add(RB);
        buttons.add(R3);
        buttons.add(LT);
        buttons.add(RT);
        buttons.add(LEFT);
        buttons.add(RIGHT);
        buttons.add(DOWN);
        buttons.add(UP);
    }
}
