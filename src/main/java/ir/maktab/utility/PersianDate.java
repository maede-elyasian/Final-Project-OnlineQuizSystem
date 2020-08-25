package ir.maktab.utility;

public class PersianDate {

    public static boolean isPersianDate(String date) {

        String regex = "^[1][1-4][0-9]{2}\\/" +
                "((0[1-6]\\/" +
                "(0[1-9]|[1-2][0-9]|3[0-1]))|(0[7-9]\\/" +
                "(0[1-9]|[1-2][0-9]|30))|(1[0-1]\\/" +
                "(0[1-9]|[1-2][0-9]|30))|(12\\/(0[1-9]|[1-2][0-9])))";
        if (date.matches(regex)) {
            return true;
        } else {
            return false;
        }
    }

}
