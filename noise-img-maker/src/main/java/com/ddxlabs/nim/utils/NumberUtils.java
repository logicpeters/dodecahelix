package com.ddxlabs.nim.utils;

public class NumberUtils {

    public static String increment(String value, boolean decrementInstead, int factor) {
        try {
            int i = Integer.parseInt(value);
            if (decrementInstead) {
                return String.valueOf(i - factor);
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
            // limit precision of double
            return String.format("%.4f", newValue);
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
