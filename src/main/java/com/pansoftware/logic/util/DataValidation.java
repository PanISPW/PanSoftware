package com.pansoftware.logic.util;

import java.time.LocalDate;
import java.time.Month;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

// @author Danilo D'Amico

public class DataValidation {

    private DataValidation(){}

    public static LocalDate setDefaultDate() {
        return LocalDate.of(1970, Month.JANUARY, 1);
    }

    public static boolean isNatural(final int number) {
        return number >= 0;
    }

    public static boolean isNotPastDate(final LocalDate date) {
        return !date.isBefore(LocalDate.now());
    }

    public static boolean isCorrectEmail(final String email) {
        boolean bool = false;
        final String regex = "\\b[A-Z0-9._%-]+@[A-Z0-9.-]+\\.[A-Z]{2,4}\\b";

        final Pattern pattern = Pattern.compile(regex);
        final Matcher matcher = pattern.matcher(email);

        if (matcher.matches())
            bool = true;

        return bool;
    }

}