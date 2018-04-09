package com.tutoring.util;

/**
 * Created by chirag.agrawal on 4/4/2018.
 */

public enum NotificationType {

    NEW_LESSON(1001);

    private int numVal;

    NotificationType(int numVal) {
        this.numVal = numVal;
    }

    public int getNumVal() {
        return numVal;
    }
}
