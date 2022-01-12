package logic.util;

import java.time.LocalDate;
import java.time.Month;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

// @author Danilo D'Amico

public class DataValidation {

    public static LocalDate setDefaultDate() {
        return LocalDate.of(1970, Month.JANUARY, 1);
    }

    public static boolean isNatural(int number) {
        boolean bool = false;
        if (number >= 0)
            bool = true;

        return bool;
    }

    public static boolean isNotPastDate(LocalDate date) {
        boolean bool = false;
        if (!date.isBefore(LocalDate.now()))
            bool = true;

        return bool;

    }

    public static boolean isCorrectEmail(String email) {
        boolean bool = false;
        String regex = "\\b[A-Z0-9._%-]+@[A-Z0-9.-]+\\.[A-Z]{2,4}\\b";

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);

        if (matcher.matches())
            bool = true;

        return bool;
    }

}