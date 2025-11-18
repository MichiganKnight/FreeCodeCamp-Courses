package com.revature.weekTwo.exceptions;

public class NegativeSpeedException extends Exception {
    public static final long serialVersionUID = 1L;

    public NegativeSpeedException() {
        super("Cannot Go a Negative Speed!");
    }
}
