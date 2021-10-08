package com.turtle.api.managers;

import com.turtle.api.BrilliantTurtle;

public class TurtleStateManager {

    public static double scale;


    protected static boolean statePushed;




    public static void scale(double newScale) {
        if (!statePushed) {
            throw new RuntimeException("State not active..");
        }
        scale = newScale;
    }
    public static void pushState() {
        statePushed = true;
    }

    public static void popState() {
        statePushed = false;
        BrilliantTurtle.getInstance().updateState();
    }
}
