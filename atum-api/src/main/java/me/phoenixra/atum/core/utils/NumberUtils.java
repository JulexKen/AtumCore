package me.phoenixra.atum.core.utils;

import org.jetbrains.annotations.NotNull;

import java.text.DecimalFormat;
import java.util.Map;
import java.util.TreeMap;

public class NumberUtils {
    private static final TreeMap<Integer, String> NUMERALS = new TreeMap<>();
    private static DecimalFormat doubleFormatter = new DecimalFormat("0.00");

    static {
        NUMERALS.put(1000, "M");
        NUMERALS.put(900, "CM");
        NUMERALS.put(500, "D");
        NUMERALS.put(400, "CD");
        NUMERALS.put(100, "C");
        NUMERALS.put(90, "XC");
        NUMERALS.put(50, "L");
        NUMERALS.put(40, "XL");
        NUMERALS.put(10, "X");
        NUMERALS.put(9, "IX");
        NUMERALS.put(5, "V");
        NUMERALS.put(4, "IV");
        NUMERALS.put(1, "I");
    }

    /**
     * Format double to string.
     * <p></p>
     * format: 0.00
     *
     * @param toFormat The number to format.
     * @return result.
     */
    @NotNull
    public static String format(final double toFormat) {
        String formatted = doubleFormatter.format(toFormat);

        return formatted.endsWith("00") ? String.valueOf((int) toFormat) : formatted;
    }

    /**
     * Get if the string can be parsed to a number
     *
     * @param value the string
     * @param allowPoint allow number with point
     * @return if string is a number
     */
    public static boolean isNumber(@NotNull String value, boolean allowPoint){
        if(allowPoint) return value.matches("\\d+.\\d+|\\d+|-\\d+.\\d+|-\\d+");
        else return value.matches("\\d+|-\\d+");
    }

    /**
     * Get Roman Numeral from number.
     *
     * @param number The number to convert.
     * @return The number, converted to a roman numeral.
     */
    @NotNull
    public static String toNumeral(final int number) {
        if (number >= 1 && number <= 4096) {
            int l = NUMERALS.floorKey(number);
            if (number == l) {
                return NUMERALS.get(number);
            }
            return NUMERALS.get(l) + toNumeral(number - l);
        } else {
            return String.valueOf(number);
        }
    }

    /**
     * Get number from roman numeral.
     *
     * @param numeral The numeral to convert.
     * @return The number, converted from a roman numeral.
     */
    public static int fromNumeral(@NotNull final String numeral) {
        if (numeral.isEmpty()) {
            return 0;
        }
        for (Map.Entry<Integer, String> entry : NUMERALS.descendingMap().entrySet()) {
            if (numeral.startsWith(entry.getValue())) {
                return entry.getKey() + fromNumeral(numeral.substring(entry.getValue().length()));
            }
        }
        return 0;
    }


    private NumberUtils() {
        throw new UnsupportedOperationException("This is an utility class and cannot be instantiated");
    }
}
