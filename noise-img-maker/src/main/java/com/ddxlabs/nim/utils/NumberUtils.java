package com.ddxlabs.nim.utils;

import java.util.Random;

public class NumberUtils {

    public static String randomize(String currentValue, Random random) {
        try {
            Integer.parseInt(currentValue);
            return String.valueOf(random.nextInt(10));
        } catch (NumberFormatException ignored) {
        }

        try {
            Double.parseDouble(currentValue);
            double newValue = random.nextDouble() * 5.0;
            return String.format("%.4f", newValue);
        } catch (NumberFormatException ignored) {
        }

        // TODO - flip a boolean??

        return currentValue;
    }

    public static String increment(String value, boolean decrementInstead, int factor) {
        try {
            int i = Integer.parseInt(value);
            if (decrementInstead) {
                // dont decrement below 0
                if ((i-factor)>=0) {
                    return String.valueOf(i - factor);
                }
            } else {
                return String.valueOf(i + factor);
            }
        } catch (NumberFormatException ignored) {
        }

        try {
            double i = Double.parseDouble(value);
            double newValue = i + (0.1d * factor);
            if (decrementInstead) {
                newValue = i - (0.1d * factor);
            }
            if (newValue>=0.0d) {
                // limit precision of double
                return String.format("%.4f", newValue);
            }
        } catch (NumberFormatException ignored) {
        }

        // TODO - flip a boolean??

        return value;
    }

    public static boolean matchesFormat(String paramValue, String previousValue) {
        // make sure doubles is a double, int is an int, etc..
        try {
            int i = Integer.parseInt(paramValue);
            int j = Integer.parseInt(previousValue);
            // both ints -- cool!
            return true;
        } catch (NumberFormatException ignored) {
        }

        try {
            double i = Double.parseDouble(paramValue);
            double j = Double.parseDouble(previousValue);
            // both doubles -- cool!
            return true;
        } catch (NumberFormatException ignored) {
        }

        try {
            boolean i = Boolean.parseBoolean(paramValue);
            boolean j = Boolean.parseBoolean(previousValue);
            // both boolean -- cool!
            return true;
        } catch (NumberFormatException ignored) {
        }

        return false;
    }
}
