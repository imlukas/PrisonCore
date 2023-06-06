package me.imlukas.prisoncore.utils;

import java.util.function.Predicate;

public class NumberUtil {

    /**
     * Parses a String to an integer, throwing an IllegalArgumentException if the String is not a valid integer.
     *
     * @param stringToParse The String to parse
     * @param predicate     A Predicate to test the parsed integer against
     * @return The parsed integer
     */
    public static int parseInt(String stringToParse, Predicate<Integer> predicate) {
        int parsed = 1;
        try {
            parsed = Integer.parseInt(stringToParse);
        } catch (NumberFormatException e) {
            System.err.println("Invalid number: " + stringToParse);
            return 1;
        }

        if (!predicate.test(parsed)) {
            System.err.println("Invalid number: " + stringToParse);
            return 1;
        }

        return parsed;
    }
}
