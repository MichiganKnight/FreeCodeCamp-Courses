package org.logger;

public class CustomLogger {
    private static final String SUCCESS_STRING = "[Success]: ";
    private static final String INFO_STRING = "[Info]: ";
    private static final String WARNING_STRING = "[Warning]: ";
    private static final String ERROR_STRING = "[Error]: ";

    private static final String SUCCESS_COLOR = "\u001B[33m";
    private static final String INFO_COLOR = "\u001B[34m";
    private static final String WARNING_COLOR = "\u001B[33m";
    private static final String ERROR_COLOR = "\u001B[31m";

    public static final String RESET = "\u001B[0m";

    public CustomLogger() {

    }

    public void success(String message) {
        System.out.println(SUCCESS_COLOR + SUCCESS_STRING + message + RESET);
    }

    public void info(String message) {
        System.out.println(INFO_COLOR + INFO_STRING + message + RESET);
    }

    public void warning(String message) {
        System.out.println(WARNING_COLOR + WARNING_STRING + message + RESET);
    }

    public void error(String message) {
        System.out.println(ERROR_COLOR + ERROR_STRING + message + RESET);
    }
}
